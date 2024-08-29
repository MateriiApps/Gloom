package com.materiiapps.gloom.domain.manager

import com.materiiapps.gloom.domain.manager.base.BasePreferenceManager
import com.materiiapps.gloom.domain.manager.base.enumPreference
import com.materiiapps.gloom.util.SettingsProvider

class DialogManager(provider: SettingsProvider) : BasePreferenceManager(provider) {

    var downloadAsset by enumPreference("release_asset_download", DialogState.UNKNOWN)

    var installApk by enumPreference("install_apk", DialogState.UNKNOWN)

}

enum class DialogState {
    UNKNOWN,
    CONFIRMED,
    DENIED
}