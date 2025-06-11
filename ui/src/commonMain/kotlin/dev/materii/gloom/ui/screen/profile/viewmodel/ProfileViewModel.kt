package dev.materii.gloom.ui.screen.profile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.materii.gloom.api.model.ModelUser
import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.fold
import dev.materii.gloom.api.util.ifSuccessful
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val gqlRepo: GraphQLRepository,
    private val username: String
): ScreenModel {

    var user: ModelUser? by mutableStateOf(null)
    private var hasErrors by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    init {
        loadData()
    }

    fun loadData() {
        if (username.isNotEmpty())
            getUser()
        else
            getCurrentUser()
    }

    private fun getCurrentUser() {
        isLoading = true
        screenModelScope.launch(Dispatchers.IO) {
            gqlRepo.getCurrentProfile().fold(
                onSuccess = {
                    isLoading = false
                    if (it.username == null) return@fold
                    user = it
                },
                onError = {
                    hasErrors = true
                    isLoading = false
                }
            )
        }
    }

    private fun getUser() {
        isLoading = true
        screenModelScope.launch(Dispatchers.IO) {
            gqlRepo.getProfile(username).fold(
                onSuccess = {
                    isLoading = false
                    if (it.username == null) return@fold
                    user = it
                },
                onError = {
                    hasErrors = true
                    isLoading = false
                }
            )
        }
    }

    fun toggleFollowing() {
        if (user?.id == null) return
        val isFollowing = user?.isFollowing

        screenModelScope.launch {
            if (isFollowing == false)
                gqlRepo.followUser(user!!.id!!).ifSuccessful {
                    user = user!!.copy(isFollowing = it.first)
                }
            else
                gqlRepo.unfollowUser(user!!.id!!).ifSuccessful {
                    user = user!!.copy(isFollowing = it.first)
                }
        }
    }

}