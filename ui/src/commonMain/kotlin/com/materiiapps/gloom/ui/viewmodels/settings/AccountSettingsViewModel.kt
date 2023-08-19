package com.materiiapps.gloom.ui.viewmodels.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.utils.ifSuccessful
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.ui.utils.clearRootNavigation
import kotlinx.coroutines.launch

class AccountSettingsViewModel(
    val authManager: AuthManager,
    private val gqlRepo: GraphQLRepository
) : ScreenModel {

    var isLoading by mutableStateOf(false)

    init {
        loadAccounts()
    }

    fun loadAccounts() {
        println(authManager.accounts)
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

}