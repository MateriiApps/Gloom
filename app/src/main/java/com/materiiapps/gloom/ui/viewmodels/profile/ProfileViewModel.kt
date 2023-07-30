package com.materiiapps.gloom.ui.viewmodels.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.materiiapps.gloom.api.models.ModelUser
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.utils.fold
import com.materiiapps.gloom.api.utils.ifSuccessful
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val gqlRepo: GraphQLRepository,
    private val username: String
) : ScreenModel {

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
        coroutineScope.launch(Dispatchers.IO) {
            gqlRepo.getCurrentProfile().fold(
                onSuccess = {
                    isLoading = false
                    if(it.username == null) return@fold
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
        coroutineScope.launch(Dispatchers.IO) {
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

        coroutineScope.launch {
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