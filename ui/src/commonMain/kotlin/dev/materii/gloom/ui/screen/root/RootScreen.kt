package dev.materii.gloom.ui.screen.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.benasher44.uuid.uuid4
import dev.materii.gloom.domain.manager.AuthManager
import dev.materii.gloom.ui.component.Avatar
import dev.materii.gloom.ui.component.navbar.LongClickableNavBarItem
import dev.materii.gloom.ui.screen.settings.component.account.AccountSwitcherSheet
import dev.materii.gloom.ui.util.DimenUtils
import dev.materii.gloom.ui.util.RootTab
import org.koin.compose.koinInject

class RootScreen: Screen {

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
        val authManager: AuthManager = koinInject()
        val navigator = LocalTabNavigator.current

        NavigationBar {
            RootTab.entries.forEach {
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