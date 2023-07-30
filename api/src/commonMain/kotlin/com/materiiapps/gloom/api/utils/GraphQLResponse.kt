package com.materiiapps.gloom.api.utils

typealias GQLErrors = List<com.apollographql.apollo3.api.Error>

sealed interface GraphQLResponse<out T> {
    data class Success<T>(val data: T, val errors: GQLErrors) : GraphQLResponse<T>
    data class Error<T>(val errors: GQLErrors) : GraphQLResponse<T>
    data class Failure<T>(val error: ApiFailure) : GraphQLResponse<T>
}

inline fun <T, R> GraphQLResponse<T>.fold(
    onSuccess: (T, GQLErrors) -> R,
    onError: (GQLErrors) -> R,
    onFailure: (ApiFailure) -> R,
): R = when(this) {
    is GraphQLResponse.Success -> onSuccess(data, errors)
    is GraphQLResponse.Error -> onError(errors)
    is GraphQLResponse.Failure -> onFailure(error)
}

inline fun <T, R> GraphQLResponse<T>.fold(
    onSuccess: (T) -> R,
    onError: (String) -> R,
): R = when(this) {
    is GraphQLResponse.Success -> onSuccess(data)
    is GraphQLResponse.Error -> onError(errors.joinToString())
    is GraphQLResponse.Failure -> onError(error.message ?: "")
}

inline fun <T> GraphQLResponse<T>.ifSuccessful(block: (T) -> Unit) {
    if (this is GraphQLResponse.Success) {
        block(data)
    }
}

fun <T> GraphQLResponse<T>.getOrNull(): T? = when(this) {
    is GraphQLResponse.Success -> data
    is GraphQLResponse.Error,
    is GraphQLResponse.Failure -> null
}

@Suppress("UNCHECKED_CAST")
fun <T, R> GraphQLResponse<T>.transform(block: (T) -> R): GraphQLResponse<R> {
    return when(this) {
        is GraphQLResponse.Success -> GraphQLResponse.Success(block(data), errors)
        is GraphQLResponse.Error -> this as GraphQLResponse.Error<R>
        is GraphQLResponse.Failure ->this as GraphQLResponse.Failure<R>
    }
}