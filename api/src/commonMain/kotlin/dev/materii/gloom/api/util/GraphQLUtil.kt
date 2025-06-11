package dev.materii.gloom.api.util

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Optional

suspend fun <D: Operation.Data> ApolloCall<D>.response(): GraphQLResponse<D> {
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