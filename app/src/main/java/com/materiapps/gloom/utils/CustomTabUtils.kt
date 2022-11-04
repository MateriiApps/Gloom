package com.materiapps.gloom.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

private var mDefaultBrowserPackage: String? = null

@Suppress("DEPRECATION")
private val Context.defaultBrowserPackage: String?
    get() {
        return if(mDefaultBrowserPackage == null) {
            mDefaultBrowserPackage = packageManager
                .queryIntentActivities(
                    Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://")),
                    0
                )
                .firstOrNull()
                ?.activityInfo?.packageName

            mDefaultBrowserPackage
        } else mDefaultBrowserPackage
    }

fun Context.openCustomTab(url: String, force: Boolean) = CustomTabsIntent.Builder().build().run {
    if(force) intent.setPackage(defaultBrowserPackage)
    launchUrl(this@openCustomTab, Uri.parse(url))
}