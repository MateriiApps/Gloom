package com.materiapps.gloom.domain.models

import androidx.compose.ui.graphics.Color
import com.materiapps.gloom.RepoListQuery
import com.materiapps.gloom.rest.dto.license.License
import com.materiapps.gloom.rest.dto.repo.Repository
import com.materiapps.gloom.rest.dto.user.User
import kotlinx.datetime.LocalDateTime

data class ModelRepo(
    val id: Long? = null,
    val name: String? = null,
    val fullName: String? = null,
    val private: Boolean? = null,
    val owner: User? = null,
    val description: String? = null,
    val fork: Boolean? = null,
    val parent: ModelRepo? = null,
    val created: LocalDateTime? = null,
    val updated: LocalDateTime? = null,
    val pushed: LocalDateTime? = null,
    val homepage: String? = null,
    val size: Long? = null,
    val stars: Int? = null,
    val watchers: Int? = null,
    val language: ModelLanguage? = null,
    val hasIssues: Boolean? = null,
    val hasProjects: Boolean? = null,
    val hasDownloads: Boolean? = null,
    val hasWiki: Boolean? = null,
    val hasPages: Boolean? = null,
    val forks: Int? = null,
    val mirror: String? = null,
    val archived: Boolean? = null,
    val disabled: Boolean? = null,
    val openIssues: Int? = null,
    val license: License? = null,
    val allowForking: Boolean? = null,
    val isTemplate: Boolean? = null,
    val signoffRequired: Boolean? = null,
    val topics: List<String>? = null,
    val visibility: Repository.Visibility? = null,
    val defaultBranch: String? = null
) {

    companion object {

        fun fromApi(repo: Repository): ModelRepo = with(repo) {
            ModelRepo(
                id = id,
                name = name,
                fullName = fullName,
                private = private,
                owner = owner,
                description = description,
                fork = fork,
                parent = if (parent != null) fromApi(parent) else null,
                created = created,
                updated = updated,
                pushed = pushed,
                homepage = homepage,
                size = size,
                stars = stars,
                watchers = watchers,
                language = if (language != null) ModelLanguage(language) else null,
                hasIssues = hasIssues,
                hasProjects = hasProjects,
                hasDownloads = hasDownloads,
                hasWiki = hasWiki,
                hasPages = hasPages,
                forks = forks,
                mirror = mirror,
                archived = archived,
                disabled = disabled,
                openIssues = openIssues,
                license = license,
                allowForking = allowForking,
                isTemplate = isTemplate,
                signoffRequired = signoffRequired,
                topics = topics,
                visibility = visibility,
                defaultBranch = defaultBranch
            )
        }

        fun fromRepoListQuery(rlq: RepoListQuery.Node) = with(rlq) {
            val lang = languages?.nodes?.firstOrNull()
            val modelLang = if (lang != null)
                ModelLanguage(lang.name, Color(android.graphics.Color.parseColor(lang.color)))
            else
                null

            ModelRepo(
                name = name,
                description = description,
                fork = isFork,
                parent = if (parent != null) ModelRepo(fullName = parent.nameWithOwner) else null,
                language = modelLang,
                stars = stargazerCount
            )
        }

    }

}