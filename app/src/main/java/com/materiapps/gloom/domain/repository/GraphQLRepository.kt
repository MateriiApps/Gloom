package com.materiapps.gloom.domain.repository

import com.materiapps.gloom.domain.models.ModelUser
import com.materiapps.gloom.rest.service.GraphQLService
import com.materiapps.gloom.rest.utils.transform

class GraphQLRepository(
    private val service: GraphQLService
) {

    suspend fun getCurrentProfile() =
        service.getCurrentProfile().transform { ModelUser.fromProfileQuery(it) }

    suspend fun getProfile(username: String) =
        service.getProfile(username).transform { ModelUser.fromUserProfileQuery(it) }

    suspend fun getRepositoriesForUser(
        username: String,
        after: String? = null,
        count: Int? = null
    ) = service.getRepositoriesForUser(username, after, count)

}