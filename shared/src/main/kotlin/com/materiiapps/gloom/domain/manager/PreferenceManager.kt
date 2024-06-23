package com.materiiapps.gloom.domain.manager

import com.materiiapps.gloom.domain.manager.base.BasePreferenceManager
import com.materiiapps.gloom.domain.manager.base.booleanPreference
import com.materiiapps.gloom.domain.manager.base.enumPreference
import com.materiiapps.gloom.domain.manager.base.intPreference
import com.materiiapps.gloom.utils.SettingsProvider
import com.materiiapps.gloom.utils.supportsMonet

class PreferenceManager(provider: SettingsProvider) :
    BasePreferenceManager(provider) {

    var theme by enumPreference("theme", Theme.SYSTEM)
    var monet by booleanPreference("monet", supportsMonet)

    var userAvatarShape by enumPreference("user_avatar_shape", AvatarShape.Circle)
    var userAvatarRadius by intPreference("user_avatar_radius", 31)

    var orgAvatarShape by enumPreference("org_avatar_shape", AvatarShape.RoundedCorner)
    var orgAvatarRadius by intPreference("org_avatar_radius", 31)

    init {
        if (userAvatarRadius > 50) userAvatarRadius = 50
        if (userAvatarRadius < 0) userAvatarRadius = 0
        if (orgAvatarRadius > 50) orgAvatarRadius = 50
        if (orgAvatarRadius < 0) orgAvatarRadius = 0
    }

}

enum class Theme {
    SYSTEM,
    LIGHT,
    DARK
}

enum class AvatarShape {
    Circle,
    RoundedCorner,
    Squircle
}