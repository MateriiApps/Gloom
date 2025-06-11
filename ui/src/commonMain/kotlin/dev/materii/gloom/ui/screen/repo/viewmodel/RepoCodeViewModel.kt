package dev.materii.gloom.ui.screen.repo.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.GraphQLResponse
import dev.materii.gloom.gql.fragment.CommitDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoCodeViewModel(
    private val gql: GraphQLRepository,
    private val owner: String,
    private val name: String
): ScreenModel {

    sealed interface UiState {
        data class Loaded(
            val id: String,
            val defaultBranch: String,
            val latestCommit: CommitDetails?
        ): UiState

        object Loading: UiState
        object Error: UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        screenModelScope.launch {
            _uiState.emit(UiState.Loading)
            val repoDefaultsRes = gql.getRepoDefaults(owner, name)

            if (repoDefaultsRes is GraphQLResponse.Success) {
                _uiState.emit(
                    UiState.Loaded(
                        id = repoDefaultsRes.data!!.id,
                        defaultBranch = repoDefaultsRes.data!!.defaultBranchRef!!.name,
                        latestCommit = null
                    )
                )
            } else {
                _uiState.emit(UiState.Error)
            }
        }
    }

}