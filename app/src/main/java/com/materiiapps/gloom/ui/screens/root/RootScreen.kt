package com.materiiapps.gloom.ui.screens.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.materiiapps.gloom.utils.RootTab

class RootScreen : Screen {

    @Composable
    override fun Content() = Screen()

    @Composable
    private fun Screen(
    ) {
        val systemNavBarHeight = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()

        TabNavigator(tab = RootTab.HOME.tab) { nav ->
            Scaffold(
                bottomBar = { TabBar() }
            ) {
                Box(Modifier.padding(bottom = it.calculateBottomPadding() - systemNavBarHeight)) {
                    nav.current.Content()
                }
            }
        }
    }

    @Composable
    private fun TabBar() {
        val navigator = LocalTabNavigator.current

        NavigationBar {
            RootTab.values().forEach {
                NavigationBarItem(
                    selected = navigator.current == it.tab,
                    onClick = { navigator.current = it.tab },
                    icon = {
                        Icon(
                            painter = it.tab.options.icon!!,
                            contentDescription = it.tab.options.title
                        )
                    },
                    label = { Text(text = it.tab.options.title) }
                )
            }
        }
    }

}