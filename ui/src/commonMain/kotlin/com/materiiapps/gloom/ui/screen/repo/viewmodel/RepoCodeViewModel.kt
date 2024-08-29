package com.materiiapps.gloom.ui.screen.repo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.util.fold
import kotlinx.coroutines.launch

class RepoCodeViewModel(
    private val gql: GraphQLRepository,
    nameWithOwner: Pair<String, String>
) : ScreenModel {

    val owner = nameWithOwner.first
    val name = nameWithOwner.second

    var hasError by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    var defaultBranch by mutableStateOf(null as String?)

    init {
        loadDefaultBranch()
    }

    fun loadDefaultBranch() {
        screenModelScope.launch {
            isLoading = true
            gql.getDefaultBranch(owner, name).fold(
                onSuccess = {
                    defaultBranch = it
                    hasError = false
                    isLoading = false
                },
                onError = {
                    hasError = true
                    isLoading = false
                }
            )
        }
    }

}