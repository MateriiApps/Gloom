package com.materiiapps.gloom.ui.utils

import cafe.adriel.voyager.core.model.ScreenModelStore
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.materiiapps.gloom.ui.screens.explore.ExploreScreen
import com.materiiapps.gloom.ui.screens.home.HomeScreen
import com.materiiapps.gloom.ui.screens.notifications.NotificationsScreen
import com.materiiapps.gloom.ui.screens.profile.ProfileScreen
import com.materiiapps.gloom.ui.screens.profile.ProfileTab

@Suppress("unused")
enum class RootTab(val tab: Tab) {
    HOME(HomeScreen()),
    EXPLORE(ExploreScreen()),
    NOTIFICATIONS(NotificationsScreen()),
    PROFILE(ProfileTab())
}

tailrec fun Navigator.navigate(screen: Screen) {
    return if (parent == null && items.firstOrNull { it.key == screen.key } == null) try {
        push(screen)
    } catch (_: Throwable) {
    }
    else parent!!.navigate(screen)
}

fun clearRootNavigation() {
    ScreenModelStore.remove(HomeScreen())
    ScreenModelStore.remove(ExploreScreen())
    ScreenModelStore.remove(NotificationsScreen())
    ScreenModelStore.remove(ProfileScreen())
    ScreenModelStore.remove(ProfileTab())
}