package dev.materii.gloom.ui.util

import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.model.ScreenModelStore
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import dev.materii.gloom.ui.screen.explore.ExploreScreen
import dev.materii.gloom.ui.screen.home.HomeScreen
import dev.materii.gloom.ui.screen.notifications.NotificationsScreen
import dev.materii.gloom.ui.screen.profile.ProfileScreen
import dev.materii.gloom.ui.screen.profile.ProfileTab

@Suppress("unused")
enum class RootTab(val tab: Tab) {

    HOME(HomeScreen()),
    EXPLORE(ExploreScreen()),
    NOTIFICATIONS(NotificationsScreen()),
    PROFILE(ProfileTab())
    
}

fun Navigator.navigate(screen: Screen) {
    if (items.any { it.key == screen.key }) return
    if (parent == null) {
        return push(screen)
    } else {
        parent!!.navigate(screen)
    }
}

@OptIn(InternalVoyagerApi::class)
fun clearRootNavigation() {
    ScreenModelStore.onDisposeNavigator(HomeScreen().key)
    ScreenModelStore.onDisposeNavigator(ExploreScreen().key)
    ScreenModelStore.onDisposeNavigator(NotificationsScreen().key)
    ScreenModelStore.onDisposeNavigator(ProfileScreen().key)
    ScreenModelStore.onDisposeNavigator(ProfileTab().key)
}