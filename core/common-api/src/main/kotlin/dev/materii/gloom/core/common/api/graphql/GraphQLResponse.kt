package dev.materii.gloom.core.common.api.graphql

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Operation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

typealias GraphQLResponseFlow<T> = Flow<GraphQLResponse<T>>
internal typealias GQLErrors = List<Error>

sealed interface GraphQLResponse<out T> {

    data class Success<T>(val data: T, val errors: GQLErrors): GraphQLResponse<T>

    data class Error<T>(val errors: GQLErrors): GraphQLResponse<T>

    data class Failure<T>(val error: Throwable): GraphQLResponse<T>

}

@Suppress("TooGenericExceptionCaught")
fun <D: Operation.Data> ApolloCall<D>.response(): GraphQLResponseFlow<D> {
    return try {
        toFlow().map { response ->
            when {
                !response.hasErrors() -> GraphQLResponse.Success(response.dataOrThrow(), emptyList())
                response.hasErrors() && response.data != null -> GraphQLResponse.Success(response.data!!, response.errors.orEmpty())
                else -> GraphQLResponse.Error(response.errors.orEmpty())
            }
        }
    } catch (e: Throwable) {
        flowOf(GraphQLResponse.Failure(e))
    }
}