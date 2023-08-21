package com.materiiapps.gloom.ui.screens.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.benasher44.uuid.uuid4
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.ui.components.Avatar
import com.materiiapps.gloom.ui.components.navbar.LongClickableNavBarItem
import com.materiiapps.gloom.ui.utils.DimenUtils
import com.materiiapps.gloom.ui.utils.RootTab
import com.materiiapps.gloom.ui.widgets.accounts.AccountSwitcherSheet
import org.koin.androidx.compose.get

class RootScreen : Screen {

    @Composable
    override fun Content() = Screen()

    override val key = "${this::class.qualifiedName}-${uuid4()}"

    @Composable
    private fun Screen() {
        var accountSwitcherVisible by remember {
            mutableStateOf(false)
        }

        if (accountSwitcherVisible) {
            AccountSwitcherSheet(
                onDismiss = { accountSwitcherVisible = false }
            )
        }

        TabNavigator(tab = RootTab.HOME.tab) { nav ->
            Scaffold(
                bottomBar = {
                    TabBar(
                        onProfileLongClick = { accountSwitcherVisible = true }
                    )
                }
            ) {
                Box(Modifier.padding(bottom = it.calculateBottomPadding() - DimenUtils.navBarPadding)) {
                    nav.current.Content()
                }
            }
        }
    }

    @Composable
    private fun TabBar(
        onProfileLongClick: () -> Unit
    ) {
        val authManager: AuthManager = get()
        val navigator = LocalTabNavigator.current

        NavigationBar {
            RootTab.values().forEach {
                LongClickableNavBarItem(
                    selected = navigator.current == it.tab,
                    onClick = { navigator.current = it.tab },
                    onLongClick = { if (it == RootTab.PROFILE) onProfileLongClick() },
                    icon = {
                        if (authManager.accounts.size > 1 && it == RootTab.PROFILE) {
                            Avatar(
                                url = authManager.currentAccount!!.avatarUrl,
                                contentDescription = it.tab.options.title,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .alpha(if (navigator.current == it.tab) 1f else 0.75f)
                            )
                        } else {
                            Icon(
                                painter = it.tab.options.icon!!,
                                contentDescription = it.tab.options.title
                            )
                        }
                    },
                    label = { Text(text = it.tab.options.title) },
                )
            }
        }
    }

}