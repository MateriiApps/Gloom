package com.materiiapps.gloom.ui.viewmodels.repo.tab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.utils.fold
import com.materiiapps.gloom.gql.fragment.RepoDetails
import kotlinx.coroutines.launch

class RepoDetailsViewModel(
    private val gql: GraphQLRepository,
    nameWithOwner: Pair<String, String>
) : ScreenModel {

    val owner = nameWithOwner.first
    val name = nameWithOwner.second

    var details by mutableStateOf(null as RepoDetails?)
    var detailsLoading by mutableStateOf(false)
    var hasError by mutableStateOf(false)

    var isStarLoading by mutableStateOf(false)

    init {
        loadDetails()
    }

    fun loadDetails() {
        coroutineScope.launch {
            detailsLoading = true
            gql.getRepoDetails(owner, name).fold(
                onSuccess = {
                    details = it
                    detailsLoading = false
                },
                onError = {
                    hasError = true
                    detailsLoading = false
                }
            )
        }
    }

    private fun updateStarDetails(starred: Boolean) {
        details = details!!.copy(
            viewerHasStarred = starred,
            stargazerCount = details!!.stargazerCount + if (starred) 1 else -1
        )
    }

    fun starRepo() {
        updateStarDetails(starred = true)

        coroutineScope.launch {
            isStarLoading = true

            gql.starRepo(details!!.id).fold(
                onSuccess = {
                    isStarLoading = false
                },
                onError = {
                    updateStarDetails(starred = false)

                    isStarLoading = false
                    hasError = true
                }
            )
        }
    }

    fun unstarRepo() {
        updateStarDetails(starred = false)

        coroutineScope.launch {
            isStarLoading = true

            gql.unstarRepo(details!!.id).fold(
                onSuccess = {
                    isStarLoading = false
                },
                onError = {
                    updateStarDetails(starred = true)

                    isStarLoading = false
                    hasError = true
                }
            )
        }
    }
}