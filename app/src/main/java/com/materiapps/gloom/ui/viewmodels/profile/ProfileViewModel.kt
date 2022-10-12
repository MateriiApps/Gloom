package com.materiapps.gloom.ui.viewmodels.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.apollographql.apollo3.ApolloClient
import com.materiapps.gloom.ProfileQuery
import com.materiapps.gloom.utils.scope
import kotlinx.coroutines.launch

class ProfileViewModel(
    client: ApolloClient
) : ScreenModel {

    var isLoading by mutableStateOf(true)
    var user: ProfileQuery.Viewer? by mutableStateOf(null)

    init {
        scope.launch {
            client.query(ProfileQuery()).execute().also {
                isLoading = false
                user = it.data?.viewer
            }
        }
    }

}