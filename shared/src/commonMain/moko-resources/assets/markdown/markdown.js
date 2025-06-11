window.onerror = (msg, url, line, column, error) => {
    window.gloom._sendMessage("error", { message: msg });
};

class Gloom {
    constructor() {}

    /**
     * Sends a message to the native app
     *
     * @param {string} name The name used to distinguish between messages
     * @param {object} message Message object sent to the client
     */
    _sendMessage(name, message) {
        const payload = message;
        payload.name = name;
        payload.id = this._id;

        window.kmpJsBridge.callNative("MDMessage", JSON.stringify(payload));
    }

    /**
     * Unescape `raw.githubusercontent.com` paths which contain escaped query parameters
     *
     * This is taken from the GitHub Android app
     */
    _unescapeImageURLs() {
        const images = Array.from(document.querySelectorAll("img"));
        for (const image of images) {
            if (!image.src) continue;

            const imgSrc = new URL(image.src);
            if (imgSrc.host === "raw.githubusercontent.com") {
                const pathWithQueryParams = decodeURIComponent(imgSrc.pathname);
                const newPath = new URL(pathWithQueryParams, "https://raw.githubusercontent.com");

                // add back any query parameters from the original URL
                const searchParams = imgSrc.searchParams;
                for (const [key, value] of searchParams) {
                    newPath.searchParams.append(key, value);
                }

                image.src = newPath.href;
            }
        }
    }

    /**
     * Sometimes images from the /blob/ route just don't work, this fixes that
     * by redirecting to the /raw/ route
     *
     * This is taken from the GitHub Android app
     */
    _applyImageLoadingWorkaround() {
        document.querySelectorAll("source, img").forEach((node) => {
            ["srcset", "src"].forEach((attrName) => {
                const attr = node.getAttribute(attrName);
                if (
                attr !== null &&
                attr.startsWith("https://github.com") &&
                attr.indexOf("/blob/") !== -1
                ) {
                    node.setAttribute(attrName, attr.replace("/blob/", "/raw/"));
                }
            });
        });
    }

    /**
     * Load the markdown html into our template, this should
     * only be called after content has loaded in the webview
     *
     * @see dev.materii.gloom.ui.widget.markdown.Markdown in the ui module
     *
     * @param {string} id Unique UUID for this markdown WebView, generated from the app
     * @param {string} html The rendered markdown HTML
     */
    load(id, html) {
        this._id = id;
        const content = document.getElementById("content");

        if (content.innerHTML == content) return;

        content.innerHTML = html;

        this._sendMessage("loaded", { message: "Loaded" });
        this._unescapeImageURLs();
        this._applyImageLoadingWorkaround();
    }
}

const start = () => {
    window.gloom = new Gloom();
};

if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", start);
} else {
    start();
}

if (typeof exports !== "undefined") {
    exports.Gloom == Gloom;
}
