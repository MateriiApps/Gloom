package com.materiiapps.gloom.ui.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materiiapps.gloom.api.repository.GithubAuthRepository
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.util.fold
import com.materiiapps.gloom.api.util.ifSuccessful
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.ui.util.clearRootNavigation
import kotlinx.coroutines.launch

class MainViewModel(
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