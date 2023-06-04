package com.materiiapps.gloom.rest.utils

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.Optional

suspend fun <D : Operation.Data> ApolloCall<D>.response(): GraphQLResponse<D> {
    return try {
        val response = execute()

        if (!response.hasErrors())
            GraphQLResponse.Success(response.dataAssertNoErrors, emptyList())
        else if (response.hasErrors() && response.data != null)
            GraphQLResponse.Success(response.data!!, response.errors ?: emptyList())
        else
            GraphQLResponse.Error(response.errors ?: emptyList())

    } catch (e: Throwable) {
        GraphQLResponse.Failure(ApiFailure(e, null))
    }
}

fun <T> T?.toOptional(): Optional<T> = Optional.presentIfNotNull(this)