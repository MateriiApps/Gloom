package com.materiiapps.gloom.ui.viewmodels.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.materiiapps.gloom.api.repository.GithubAuthRepository
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.utils.ifSuccessful
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.ui.utils.clearRootNavigation
import kotlinx.coroutines.launch

class AccountSettingsViewModel(
    val authManager: AuthManager,
    private val gqlRepo: GraphQLRepository,
    private val authRepo: GithubAuthRepository
) : ScreenModel {

    var isLoading by mutableStateOf(false)

    var isEditMode by mutableStateOf(false)

    var signOutDialogOpen by mutableStateOf(false)

    var attemptedSignOutId by mutableStateOf(null as String?)
        private set

    var signedOut by mutableStateOf(false)
        private set

    var wasCurrent: Boolean = false
        private set

    init {
        loadAccounts()
    }

    fun openSignOutDialog(id: String) {
        authManager.accounts[id] ?: return
        attemptedSignOutId = id
        signOutDialogOpen = true
    }

    fun closeSignOutDialog() {
        signedOut = false
        attemptedSignOutId = null
        wasCurrent = false
        signOutDialogOpen = false
    }

    fun signOut(id: String) {
        val account = authManager.accounts[id] ?: return
        val token = account.token
        coroutineScope.launch {
            authRepo.deleteAccessToken(token).ifSuccessful { ->
                wasCurrent = authManager.currentAccount?.id == id
                authManager.removeAccount(account.id)
                authManager.clearApolloCache()
                clearRootNavigation()
                signedOut = true
            }
        }
    }

    fun loadAccounts() {
        coroutineScope.launch {
            isLoading = true
            gqlRepo.getAccountsByIds(authManager.accounts.keys.toList()).ifSuccessful {
                it.forEach { user ->
                    authManager.editAccount(
                        user.id,
                        avatarUrl = user.avatarUrl,
                        username = user.login,
                        displayName = user.name,
                        notificationCount = user.notificationListsWithThreadCount.totalCount
                    )
                }
            }
            isLoading = false
        }
    }

    fun switchToAccount(id: String) {
        authManager.switchToAccount(id)
        clearRootNavigation()
    }

    fun signOutAll() {
        wasCurrent = true
        authManager.accounts.keys.forEach {
            authManager.removeAccount(it)
        }
        signedOut = true
    }

}