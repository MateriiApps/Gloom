package dev.materii.gloom.core.common.api.graphql

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

/**
 * Handle all cases for GraphQL responses
 *
 * @param onSuccess Called when a query successfully return data
 * @param onError Called when a query could not be executed
 * @param onFailure Called when an error is thrown while making a request or performing serialization
 */
inline fun <T, R> GraphQLResponse<T>.fold(
    onSuccess: (T, GQLErrors) -> R,
    onError: (GQLErrors) -> R,
    onFailure: (Throwable) -> R,
): R = when (this) {
    is GraphQLResponse.Success -> onSuccess(data, errors)
    is GraphQLResponse.Error -> onError(errors)
    is GraphQLResponse.Failure -> onFailure(error)
}

/**
 * Handle all cases for GraphQL responses
 *
 * @param onSuccess Called when a query successfully return data
 * @param onError Called when a query could not be executed or an error is thrown
 * while making a request or performing serialization
 */
inline fun <T, R> GraphQLResponse<T>.fold(
    onSuccess: (T) -> R,
    onError: (String) -> R,
): R = when (this) {
    is GraphQLResponse.Success -> onSuccess(data)
    is GraphQLResponse.Error -> onError(errors.joinToString())
    is GraphQLResponse.Failure -> onError(error.message ?: "")
}

/**
 * Runs the provided [block] when the query successfully executes
 */
inline fun <T> GraphQLResponse<T>.ifSuccessful(block: (T) -> Unit) {
    if (this is GraphQLResponse.Success) {
        block(data)
    }
}

/**
 * Runs the provided [block] when the query could not successfully execute
 */
inline fun <T> GraphQLResponse<T>.ifUnsuccessful(block: (String) -> Unit) {
    fold(
        onSuccess = {},
        onError = block
    )
}

/**
 * Returns the query's data if successfully executed, otherwise return null
 */
fun <T> GraphQLResponse<T>.getOrNull(): T? = when (this) {
    is GraphQLResponse.Success -> data
    is GraphQLResponse.Error,
    is GraphQLResponse.Failure -> null
}

/**
 * Transform the response data into a more desirable form
 */
@Suppress("UNCHECKED_CAST")
fun <T, R> Flow<GraphQLResponse<T>>.transform(block: (T) -> R): Flow<GraphQLResponse<R>> {
    return mapLatest { res ->
        when (res) {
            is GraphQLResponse.Success -> GraphQLResponse.Success(block(res.data), res.errors)
            is GraphQLResponse.Error -> res as GraphQLResponse.Error<R>
            is GraphQLResponse.Failure -> res as GraphQLResponse.Failure<R>
        }
    }
}