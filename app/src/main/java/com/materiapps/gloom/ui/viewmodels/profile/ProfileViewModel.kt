package com.materiapps.gloom.ui.viewmodels.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.apollographql.apollo3.ApolloClient
import com.materiapps.gloom.ProfileQuery
import com.materiapps.gloom.UserProfileQuery
import com.materiapps.gloom.domain.models.ModelUser
import com.materiapps.gloom.domain.repository.GithubRepository
import com.materiapps.gloom.rest.utils.GraphQLUtils.response
import com.materiapps.gloom.rest.utils.fold
import com.materiapps.gloom.rest.utils.ifSuccessful
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val client: ApolloClient,
    private val repo: GithubRepository,
    private val username: String
) : ScreenModel {

    var user: ModelUser? by mutableStateOf(null)
    var readMe: String by mutableStateOf("")
    var hasErrors by mutableStateOf(false)

    init {
        if(username.isNotEmpty())
            getUser()
        else
            getCurrentUser()
    }

    private fun getCurrentUser() {
        coroutineScope.launch(Dispatchers.IO) {
            client.query(ProfileQuery()).response().fold(
                success = {
                    user = ModelUser.fromProfileQuery(it)
                    repo.getRepoReadMe(it.viewer.login, it.viewer.login).ifSuccessful { res ->
                        readMe = res
                    }
                },
                fail = {
                    hasErrors = true
                }
            )
        }
    }

    private fun getUser() {
        coroutineScope.launch(Dispatchers.IO) {
            client.query(UserProfileQuery(username)).response().fold(
                success = {
                    if(it.user == null) return@launch
                    user = ModelUser.fromUserProfileQuery(it)
                    repo.getRepoReadMe(it.user.login, it.user.login).ifSuccessful { res ->
                        readMe = res
                    }
                },
                fail = {
                    hasErrors = true
                }
            )
        }
    }

}