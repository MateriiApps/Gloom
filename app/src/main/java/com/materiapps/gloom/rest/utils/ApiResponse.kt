package com.materiapps.gloom.rest.utils

import io.ktor.http.*

sealed interface ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>
    data class Error<T>(val error: ApiError) : ApiResponse<T>
    data class Failure<T>(val error: ApiFailure) : ApiResponse<T>
    data class GQLError<T>(val errors: List<com.apollographql.apollo3.api.Error>) : ApiResponse<T>
}

class ApiError(code: HttpStatusCode, body: String?) : Error("HTTP Code $code, Body: $body")

class ApiFailure(error: Throwable, body: String?) : Error(body, error)

inline fun <T> ApiResponse<T>.fold(
    success: (T) -> Unit,
    error: (ApiError) -> Unit,
    failure: (ApiFailure) -> Unit,
    gqlError: (List<com.apollographql.apollo3.api.Error>) -> Unit
) = when (this) {
    is ApiResponse.Success -> success(data)
    is ApiResponse.Error -> error(this.error)
    is ApiResponse.Failure -> failure(this.error)
    is ApiResponse.GQLError -> gqlError(errors)
}


inline fun <T> ApiResponse<T>.fold(
    success: (T) -> Unit,
    fail: (Error) -> Unit
) = when (this) {
    is ApiResponse.Success -> success(data)
    is ApiResponse.Error -> fail(error)
    is ApiResponse.Failure -> fail(error)
    is ApiResponse.GQLError -> fail(Error(errors.joinToString()))
}

inline fun <T> ApiResponse<T>.ifSuccessful(crossinline block: (T) -> Unit) {
    if (this is ApiResponse.Success) block(data)
}