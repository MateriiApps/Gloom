package com.materiapps.gloom.rest.utils

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.Operation

suspend fun <D : Operation.Data> ApolloCall<D>.response(): ApiResponse<D> {
    return try {
        val response = this.execute()

        response.errors

        if (!response.hasErrors())
            ApiResponse.Success(response.dataAssertNoErrors)
        else
            ApiResponse.GQLError(response.errors ?: emptyList())
    } catch (e: Throwable) {
        ApiResponse.Failure(ApiFailure(e, null))
    }
}