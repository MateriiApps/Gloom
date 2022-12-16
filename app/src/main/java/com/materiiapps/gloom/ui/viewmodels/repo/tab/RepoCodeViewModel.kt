package com.materiiapps.gloom.ui.viewmodels.repo.tab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.materiiapps.gloom.domain.repository.GraphQLRepository
import com.materiiapps.gloom.rest.utils.fold
import kotlinx.coroutines.launch

class RepoCodeViewModel(
    private val gql: GraphQLRepository,
    nameWithOwner: Pair<String, String>
): ScreenModel {

    val owner = nameWithOwner.first
    val name = nameWithOwner.second

    var hasError by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    var defaultBranch by mutableStateOf(null as String?)

    init {
        loadDefaultBranch()
    }

    fun loadDefaultBranch() {
        coroutineScope.launch {
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