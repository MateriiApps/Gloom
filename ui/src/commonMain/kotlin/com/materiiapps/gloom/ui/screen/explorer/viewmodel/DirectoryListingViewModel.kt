package com.materiiapps.gloom.ui.screen.explorer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.utils.fold
import com.materiiapps.gloom.gql.fragment.FileEntryFragment
import kotlinx.coroutines.launch

class DirectoryListingViewModel(
    private val gql: GraphQLRepository,
    private val details: Details
) : ScreenModel {

    val entries = mutableStateListOf<FileEntryFragment>()
    var isLoading by mutableStateOf(false)

    init {
        loadEntries()
    }

    fun loadEntries() {
        screenModelScope.launch {
            isLoading = true
            gql.getRepoFiles(details.owner, details.name, details.branchAndPath).fold(
                onSuccess = { directory ->
                    entries.clear()
                    entries.addAll(directory.sortedByDescending { it.type == "tree" })
                    isLoading = false
                },
                onError = {
                    isLoading = false
                }
            )
        }
    }

    data class Details(
        val owner: String,
        val name: String,
        val branchAndPath: String
    )

}