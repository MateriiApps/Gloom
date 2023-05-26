package com.materiiapps.gloom.ui.viewmodels.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.ScreenModelStore
import cafe.adriel.voyager.core.model.coroutineScope
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.apolloStore
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.domain.repository.GithubAuthRepository
import com.materiiapps.gloom.rest.utils.ifSuccessful
import com.materiiapps.gloom.ui.screens.profile.ProfileScreen
import com.materiiapps.gloom.ui.screens.profile.ProfileTab
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repo: GithubAuthRepository,
    private val auth: AuthManager,
    private val gqlClient: ApolloClient
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
        coroutineScope.launch {
            repo.deleteAccessToken(token).ifSuccessful { ->
                auth.authToken = ""
                gqlClient.apolloStore.clearAll()
                ScreenModelStore.remove(ProfileScreen())
                ScreenModelStore.remove(ProfileTab())
                signedOut = true
            }
        }
    }

}