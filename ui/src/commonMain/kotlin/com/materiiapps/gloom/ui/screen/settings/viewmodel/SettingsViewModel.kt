package com.materiiapps.gloom.ui.screen.settings.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.materiiapps.gloom.api.repository.GithubAuthRepository
import com.materiiapps.gloom.api.util.ifSuccessful
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.ui.util.clearRootNavigation
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repo: GithubAuthRepository,
    private val auth: AuthManager
) : ScreenModel {

    var signOutDialogOpened by mutableStateOf(false)
        private set

    var signedOut by mutableStateOf(false)
        private set

    fun openSignOutDialog() {
        signOutDialogOpened = true
    }

    fun closeSignOutDialog() {
        signOutDialogOpened = false
    }

    fun signOut() {
        val token = auth.authToken
        screenModelScope.launch {
            repo.deleteAccessToken(token).ifSuccessful { ->
                auth.removeAccount(auth.currentAccount!!.id)
                auth.clearApolloCache()
                clearRootNavigation()
                signedOut = true
            }
        }
    }

}