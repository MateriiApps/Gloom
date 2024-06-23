package com.materiiapps.gloom.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

private var mDefaultBrowserPackage: String? = null

private val Context.defaultBrowserPackage: String?
    get() {
        return if (mDefaultBrowserPackage == null) {
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
    if (force) intent.setPackage(defaultBrowserPackage)
    if (this@openCustomTab !is Activity) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    launchUrl(this@openCustomTab, Uri.parse(url))
}

fun Context.openLink(url: Uri, forceCustomTab: Boolean = false) {
    // TODO: Setting to disable custom tabs
    openCustomTab(url.toString(), force = forceCustomTab)
}