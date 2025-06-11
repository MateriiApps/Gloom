package dev.materii.gloom.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
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
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.domain.manager.Account
import dev.materii.gloom.ui.component.DividerWithLabel
import dev.materii.gloom.ui.component.LoadingButton
import dev.materii.gloom.ui.icon.Social
import dev.materii.gloom.ui.icon.social.GitHub
import dev.materii.gloom.ui.screen.auth.viewmodel.LandingViewModel
import dev.materii.gloom.ui.screen.settings.component.account.AccountItem
import dev.materii.gloom.util.toImmutableList

class LandingScreen(
    private val showAccountCard: Boolean = true
): Screen {

    @Composable
    override fun Content() = Screen()

    @Composable
    private fun Screen() {
        val linkHandler = dev.materii.gloom.util.LocalLinkHandler.current
        val viewModel: LandingViewModel = koinScreenModel()

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