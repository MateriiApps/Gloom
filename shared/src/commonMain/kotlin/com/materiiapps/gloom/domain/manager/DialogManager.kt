package com.materiiapps.gloom.domain.manager

import android.content.Context
import com.materiiapps.gloom.domain.manager.base.BasePreferenceManager
import com.materiiapps.gloom.utils.SettingsProvider

class DialogManager(provider: SettingsProvider): BasePreferenceManager(provider) {

    var downloadAsset by enumPreference("release_asset_download", DialogState.UNKNOWN)

    var installApk by enumPreference("install_apk", DialogState.UNKNOWN)

}

enum class DialogState {
    UNKNOWN,
    CONFIRMED,
    DENIED
}