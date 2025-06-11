package dev.materii.gloom.domain.manager

import dev.materii.gloom.domain.manager.base.BasePreferenceManager
import dev.materii.gloom.domain.manager.base.enumPreference
import dev.materii.gloom.util.SettingsProvider

class DialogManager(provider: SettingsProvider): BasePreferenceManager(provider) {

    var downloadAsset by enumPreference("release_asset_download", DialogState.UNKNOWN)

    var installApk by enumPreference("install_apk", DialogState.UNKNOWN)

}

enum class DialogState {
    UNKNOWN,
    CONFIRMED,
    DENIED
}