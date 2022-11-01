package com.materiapps.gloom.rest.dto.repo

import com.materiapps.gloom.rest.dto.license.License
import com.materiapps.gloom.rest.dto.user.User
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repository(

    val id: Long,

    val name: String,

    @SerialName("full_name")
    val fullName: String,

    val private: Boolean,

    val owner: User,

    val description: String? = null,

    val fork: Boolean,

    val parent: Repository? = null,

    @SerialName("created_at")
    val created: LocalDateTime,

    @SerialName("updated_at")
    val updated: LocalDateTime,

    @SerialName("pushed_at")
    val pushed: LocalDateTime,

    val homepage: String? = null,

    val size: Long,

    @SerialName("stargazers_count")
    val stars: Int,

    @SerialName("watchers_count")
    val watchers: Int,

    val language: String? = null,

    @SerialName("has_issues")
    val hasIssues: Boolean,

    @SerialName("has_projects")
    val hasProjects: Boolean,

    @SerialName("has_downloads")
    val hasDownloads: Boolean,

    @SerialName("has_wiki")
    val hasWiki: Boolean,

    @SerialName("has_pages")
    val hasPages: Boolean,

    val forks: Int,

    @SerialName("mirror_url")
    val mirror: String? = null,

    val archived: Boolean,

    val disabled: Boolean,

    @SerialName("open_issues_count")
    val openIssues: Int,

    val license: License? = null,

    val allowForking: Boolean,

    val isTemplate: Boolean,

    @SerialName("web_commit_signoff_required")
    val signoffRequired: Boolean,

    val topics: List<String>,

    val visibility: Visibility,

    val defaultBranch: String

) {

    @Serializable
    enum class Visibility {

        @SerialName("public")
        PUBLIC,

        @SerialName("private")
        PRIVATE,

    }

}