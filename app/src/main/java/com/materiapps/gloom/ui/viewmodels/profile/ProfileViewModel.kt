package com.materiapps.gloom.ui.viewmodels.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.materiapps.gloom.domain.models.ModelUser
import com.materiapps.gloom.domain.repository.GithubRepository
import com.materiapps.gloom.domain.repository.GraphQLRepository
import com.materiapps.gloom.rest.utils.fold
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val gqlRepo: GraphQLRepository,
    private val repo: GithubRepository,
    private val username: String
) : ScreenModel {

    var user: ModelUser? by mutableStateOf(null)
    var hasErrors by mutableStateOf(false)

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
        coroutineScope.launch(Dispatchers.IO) {
            gqlRepo.getCurrentProfile().fold(
                onSuccess = {
                    if(it.username == null) return@fold
                    user = it
                },
                onError = {
                    hasErrors = true
                }
            )
        }
    }

    private fun getUser() {
        coroutineScope.launch(Dispatchers.IO) {
            gqlRepo.getProfile(username).fold(
                onSuccess = {
                    if(it.username == null) return@fold
                    user = it
                },
                onError = {
                    hasErrors = true
                }
            )
        }
    }

}