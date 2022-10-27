package com.materiapps.gloom.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

private var mDefaultBrowserPackage: String? = null

private val Context.defaultBrowserPackage: String?
    get() {
        return if(mDefaultBrowserPackage == null) {
            mDefaultBrowserPackage = packageManager
                .queryIntentActivities(
                    Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://")),
                    PackageManager.ResolveInfoFlags.of(0)
                )
                .firstOrNull()
                ?.activityInfo?.packageName

            mDefaultBrowserPackage
        } else mDefaultBrowserPackage
    }

fun Context.openUrl(url: String) = CustomTabsIntent.Builder().build().run {
    intent.setPackage(defaultBrowserPackage)
    launchUrl(this@openUrl, Uri.parse("" + url))
}