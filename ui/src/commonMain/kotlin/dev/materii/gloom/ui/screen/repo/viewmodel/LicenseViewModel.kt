package dev.materii.gloom.ui.screen.repo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.materii.gloom.api.repository.GraphQLRepository
import dev.materii.gloom.api.util.ifSuccessful
import dev.materii.gloom.gql.fragment.RepoLicense
import kotlinx.coroutines.launch

class LicenseViewModel(
    private val graphQLRepository: GraphQLRepository,
    nameWithOwner: Pair<String, String>
) : ScreenModel {

    private val owner = nameWithOwner.first
    private val name = nameWithOwner.second

    var license by mutableStateOf<RepoLicense?>(null)
        private set

    var licenseText by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        loadLicense()
    }

    private fun loadLicense() {
        screenModelScope.launch {
            isLoading = true
            graphQLRepository.getRepoLicense(owner, name).ifSuccessful { (text, details) ->
                license = details
                licenseText = text
            }
            isLoading = false
        }
    }

}