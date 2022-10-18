package com.materiapps.gloom.rest.utils

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.Operation
import com.materiapps.gloom.domain.manager.AuthManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object GraphQLUtils : KoinComponent {

    val auth: AuthManager by inject()

    suspend fun <D : Operation.Data> ApolloCall<D>.response(): ApiResponse<D> {
        return try {
            addHttpHeader("authorization", "Bearer ${auth.authToken}")
            val response = execute()

            println(response.errors)

            if (!response.hasErrors())
                ApiResponse.Success(response.dataAssertNoErrors)
            else
                ApiResponse.GQLError(response.errors ?: emptyList())
        } catch (e: Throwable) {
            ApiResponse.Failure(ApiFailure(e, null))
        }
    }
}
