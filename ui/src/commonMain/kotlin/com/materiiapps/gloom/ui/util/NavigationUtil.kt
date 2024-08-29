package com.materiiapps.gloom.ui.util

import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.model.ScreenModelStore
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.materiiapps.gloom.ui.screen.explore.ExploreScreen
import com.materiiapps.gloom.ui.screen.home.HomeScreen
import com.materiiapps.gloom.ui.screen.notifications.NotificationsScreen
import com.materiiapps.gloom.ui.screen.profile.ProfileScreen
import com.materiiapps.gloom.ui.screen.profile.ProfileTab

@Suppress("unused")
enum class RootTab(val tab: Tab) {
    HOME(HomeScreen()),
    EXPLORE(ExploreScreen()),
    NOTIFICATIONS(NotificationsScreen()),
    PROFILE(ProfileTab())
}

fun Navigator.navigate(screen: Screen) {
    return if (parent == null && items.firstOrNull { it.key == screen.key } == null) try {
        push(screen)
    } catch (_: Throwable) {
    }
    else parent!!.navigate(screen)
}

@OptIn(InternalVoyagerApi::class)
fun clearRootNavigation() {
    ScreenModelStore.onDisposeNavigator(HomeScreen().key)
    ScreenModelStore.onDisposeNavigator(ExploreScreen().key)
    ScreenModelStore.onDisposeNavigator(NotificationsScreen().key)
    ScreenModelStore.onDisposeNavigator(ProfileScreen().key)
    ScreenModelStore.onDisposeNavigator(ProfileTab().key)
}