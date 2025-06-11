package dev.materii.gloom.domain.manager

import dev.materii.gloom.domain.manager.base.BasePreferenceManager
import dev.materii.gloom.domain.manager.base.booleanPreference
import dev.materii.gloom.domain.manager.base.enumPreference
import dev.materii.gloom.domain.manager.base.intPreference
import dev.materii.gloom.domain.manager.enums.AppIcon
import dev.materii.gloom.domain.manager.enums.AvatarShape
import dev.materii.gloom.domain.manager.enums.Theme
import dev.materii.gloom.domain.manager.enums.TrendingPeriodPreference
import dev.materii.gloom.util.SettingsProvider

class PreferenceManager(provider: SettingsProvider):
    BasePreferenceManager(provider) {

    var theme by enumPreference("theme", Theme.SYSTEM)
    var monet by booleanPreference("monet", dev.materii.gloom.util.supportsMonet)

    var userAvatarShape by enumPreference("user_avatar_shape", AvatarShape.Circle)
    var userAvatarRadius by intPreference("user_avatar_radius", 31)

    var orgAvatarShape by enumPreference("org_avatar_shape", AvatarShape.RoundedCorner)
    var orgAvatarRadius by intPreference("org_avatar_radius", 31)

    var trendingPeriod by enumPreference("trending_period", TRENDING_PERIOD)

    var appIcon by enumPreference("app_icon", AppIcon.Main)

    init {
        if (userAvatarRadius > 50) userAvatarRadius = 50
        if (userAvatarRadius < 0) userAvatarRadius = 0
        if (orgAvatarRadius > 50) orgAvatarRadius = 50
        if (orgAvatarRadius < 0) orgAvatarRadius = 0
    }

    companion object Defaults {

        val TRENDING_PERIOD = TrendingPeriodPreference.DAILY

    }

}