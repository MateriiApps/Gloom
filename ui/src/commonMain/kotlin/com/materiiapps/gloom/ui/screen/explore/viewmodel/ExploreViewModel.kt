package com.materiiapps.gloom.ui.screen.explore.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.materiiapps.gloom.api.repository.GraphQLRepository
import com.materiiapps.gloom.api.util.fold
import com.materiiapps.gloom.api.util.ifSuccessful
import com.materiiapps.gloom.domain.manager.PreferenceManager
import com.materiiapps.gloom.domain.manager.TrendingPeriodPreference
import com.materiiapps.gloom.gql.fragment.TrendingRepository
import com.materiiapps.gloom.gql.type.TrendingPeriod
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val graphQLRepository: GraphQLRepository,
    private val preferenceManager: PreferenceManager
): ScreenModel {

    val trendingRepos = mutableStateListOf<TrendingRepository>()

    val repoStarsPending = mutableStateListOf<String>()

    var isLoading by mutableStateOf(false)
        private set

    var trendingPeriod by mutableStateOf(preferenceManager.trendingPeriod)
        private set

    init {
        loadTrending()
    }

    // PUBLIC

    fun loadTrending() {
        screenModelScope.launch {
            isLoading = true
            graphQLRepository.getTrending(trendingPeriod.toApi()).ifSuccessful { trending ->
                trendingRepos.clear()
                trendingRepos.addAll(trending)
            }
            isLoading = false
        }
    }

    fun updateTrendingPeriod(newPeriod: TrendingPeriodPreference) {
        trendingPeriod = newPeriod
        preferenceManager.trendingPeriod = newPeriod
        trendingRepos.clear()
        loadTrending()
    }

    fun starRepo(id: String) {
        screenModelScope.launch {
            repoStarsPending.add(id)
            editRepo(id) { it.copy(viewerHasStarred = true)}
            graphQLRepository.starRepo(id).fold(
                onSuccess = { _, _ -> },
                onError = {
                    editRepo(id) { it.copy(viewerHasStarred = false)}
                },
                onFailure = {
                    editRepo(id) { it.copy(viewerHasStarred = false)}
                }
            )
            repoStarsPending.remove(id)
        }
    }

    fun unstarRepo(id: String) {
        screenModelScope.launch {
            repoStarsPending.add(id)
            editRepo(id) { it.copy(viewerHasStarred = false)}
            graphQLRepository.unstarRepo(id).fold(
                onSuccess = { _, _ -> },
                onError = {
                    editRepo(id) { it.copy(viewerHasStarred = true)}
                },
                onFailure = {
                    editRepo(id) { it.copy(viewerHasStarred = true)}
                }
            )
            repoStarsPending.remove(id)
        }
    }

    // PRIVATE

    private fun editRepo(id: String, block: (TrendingRepository) -> TrendingRepository) {
        val targetIndex = trendingRepos.indexOfFirst { it.id == id }
        val target = trendingRepos[targetIndex]
        trendingRepos[targetIndex] = block(target)
    }

    private fun TrendingPeriodPreference.toApi(): TrendingPeriod {
        return when (this) {
            TrendingPeriodPreference.MONTHLY -> TrendingPeriod.MONTHLY
            TrendingPeriodPreference.WEEKLY -> TrendingPeriod.WEEKLY
            TrendingPeriodPreference.DAILY -> TrendingPeriod.DAILY
        }
    }

}