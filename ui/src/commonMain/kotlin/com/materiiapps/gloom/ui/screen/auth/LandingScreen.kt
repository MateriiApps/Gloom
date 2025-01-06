package com.materiiapps.gloom.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.domain.manager.Account
import com.materiiapps.gloom.ui.component.DividerWithLabel
import com.materiiapps.gloom.ui.component.LoadingButton
import com.materiiapps.gloom.ui.icon.social.GitHub
import com.materiiapps.gloom.ui.icon.Social
import com.materiiapps.gloom.ui.screen.root.RootScreen
import com.materiiapps.gloom.util.toImmutableList
import com.materiiapps.gloom.ui.screen.auth.viewmodel.LandingViewModel
import com.materiiapps.gloom.ui.screen.settings.component.account.AccountItem
import com.materiiapps.gloom.util.LocalLinkHandler
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class LandingScreen(
    private val showAccountCard: Boolean = true
) : Screen {

    @Composable
    override fun Content() = Screen()

    @Composable
    private fun Screen() {
        val linkHandler = LocalLinkHandler.current
        val viewModel: LandingViewModel = koinScreenModel()
        val nav = LocalNavigator.currentOrThrow

        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.weight(0.20f))

                Image(
                    painter = painterResource(Res.images.gloom_logo),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )

                Spacer(modifier = Modifier.weight(0.10f))

                Text(
                    text = stringResource(Res.strings.msg_welcome),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Thin
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                if (viewModel.authManager.accounts.size > 0 && showAccountCard) {
                    val accounts by remember(viewModel.authManager.accounts) {
                        derivedStateOf {
                            viewModel.authManager.accounts.values.toList().toImmutableList()
                        }
                    }

                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        ElevatedCard(
                            modifier = Modifier.heightIn(max = ((77 * 3) + 12).dp)
                        ) {
                            Text(
                                text = stringResource(Res.strings.label_choose_account),
                                style = MaterialTheme.typography.labelLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            )

                            LazyColumn {
                                items(accounts) { account ->
                                    AccountItem(
                                        account = account,
                                        isCurrent = false,
                                        onClick = {
                                            viewModel.switchToAccount(account.id)
                                            nav.replaceAll(RootScreen())
                                        }
                                    )
                                }
                            }
                        }
                    }

                    DividerWithLabel(
                        label = { Text(stringResource(Res.strings.label_or)) },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
                    )
                }

                LoadingButton(
                    loading = viewModel.authManager.loading,
                    onClick = {
                        viewModel.authManager.setAuthState(authType = Account.Type.REGULAR)
                        linkHandler.openLink(viewModel.url, forceCustomTab = true)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Social.GitHub,
                        contentDescription = null
                    )
                    Text(stringResource(Res.strings.action_github_sign_in))
                }
                Spacer(Modifier.weight(0.20f))
            }
        }
    }

}