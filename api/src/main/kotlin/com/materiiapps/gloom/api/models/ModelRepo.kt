package com.materiiapps.gloom.api.models

import com.materiiapps.gloom.api.dto.license.License
import com.materiiapps.gloom.api.dto.repo.Repository
import com.materiiapps.gloom.api.dto.user.User
import com.materiiapps.gloom.gql.RepoListQuery
import com.materiiapps.gloom.gql.StarredReposQuery
import com.materiiapps.gloom.gql.fragment.PinnedRepo
import kotlinx.datetime.LocalDateTime

data class ModelRepo(
    val id: Long? = null,
    val name: String? = null,
    val fullName: String? = null,
    val private: Boolean? = null,
    val owner: ModelUser? = null,
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
) : Pinnable {

    companion object {

        fun fromApi(repo: Repository): ModelRepo = with(repo) {
            ModelRepo(
                id = id,
                name = name,
                fullName = fullName,
                private = private,
                owner = ModelUser.fromApi(owner),
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

        fun fromRepoListQuery(rlq: RepoListQuery.Node) = with(rlq.repoListRepoFragment) {
            val lang = languages?.nodes?.firstOrNull()
            val modelLang = if (lang != null) {
                ModelLanguage(lang.language.name, lang.language.color)
            } else null

            ModelRepo(
                name = name,
                description = description,
                fork = isFork,
                parent = if (parent != null) ModelRepo(fullName = parent.nameWithOwner) else null,
                language = modelLang,
                stars = stargazerCount
            )
        }

        fun fromStarredReposQuery(srq: StarredReposQuery.Node) = with(srq.repoListRepoFragment) {
            val lang = languages?.nodes?.firstOrNull()
            val modelLang = if (lang != null)
                ModelLanguage(lang.language.name, lang.language.color)
            else
                null

            ModelRepo(
                name = name,
                description = description,
                fork = isFork,
                parent = if (parent != null) ModelRepo(fullName = parent.nameWithOwner) else null,
                language = modelLang,
                stars = stargazerCount,
                owner = ModelUser(
                    username = owner.login,
                    avatar = owner.avatarUrl,
                    type = if (owner.__typename == "User") User.Type.USER else User.Type.ORG
                )
            )
        }

        fun fromPinnedRepo(repo: PinnedRepo?) = with(repo) {
            if (this == null) return@with null

            val lang = primaryLanguage
            val modelLang = if (lang != null)
                ModelLanguage(lang.name, lang.color)
            else
                null

            ModelRepo(
                name = name,
                description = description,
                fork = isFork,
                parent = if (parent != null) ModelRepo(fullName = parent.nameWithOwner) else null,
                language = modelLang,
                stars = stargazerCount,
                owner = ModelUser(
                    username = owner.login,
                    avatar = owner.avatarUrl.toString(),
                    type = if (owner.__typename == "User") User.Type.USER else User.Type.ORG
                )
            )
        }

    }

}