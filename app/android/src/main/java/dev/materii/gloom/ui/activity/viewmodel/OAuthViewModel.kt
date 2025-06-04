package dev.materii.gloom.ui.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.materii.gloom.api.repository.GithubAuthRepository
import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.fold
import dev.materii.gloom.api.util.ifSuccessful
import dev.materii.gloom.domain.manager.AuthManager
import dev.materii.gloom.ui.util.clearRootNavigation
import kotlinx.coroutines.launch

class OAuthViewModel(
    private val gqlRepository: GraphQLRepository,
    private val authRepository: GithubAuthRepository,
    val authManager: AuthManager
) : ViewModel() {

    fun loginWithOAuthCode(code: String, onLoggedIn: () -> Unit) {
        viewModelScope.launch {
            authManager.setAuthState(loading = true)
            authRepository.getAccessToken(code).fold(
                success = { token ->
                    gqlRepository.getAccountInfo(token.accessToken).ifSuccessful { account ->
                        authManager.addAccount(
                            id = account.id,
                            token = token.accessToken,
                            type = authManager.awaitingAuthType!!,
                            username = account.login,
                            avatarUrl = account.avatarUrl,
                            displayName = account.name,
                            notificationCount = account.notificationListsWithThreadCount.totalCount
                        )
                        authManager.switchToAccount(account.id)
                        clearRootNavigation()
                        onLoggedIn()
                    }
                    clearAuthState()
                },
                error = { clearAuthState() },
                failure = { clearAuthState() },
                empty = { clearAuthState() }
            )
        }
    }

    private fun clearAuthState() = authManager.setAuthState(authType = null, loading = false)

}