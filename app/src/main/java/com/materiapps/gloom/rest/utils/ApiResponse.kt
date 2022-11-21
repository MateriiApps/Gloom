package com.materiapps.gloom.rest.utils

import io.ktor.http.HttpStatusCode

sealed interface ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>
    class Empty<T> : ApiResponse<T>
    data class Error<T>(val error: ApiError) : ApiResponse<T>
    data class Failure<T>(val error: ApiFailure) : ApiResponse<T>
}

class ApiError(code: HttpStatusCode, body: String?) : Error("HTTP Code $code, Body: $body")

class ApiFailure(error: Throwable, body: String?) : Error(body, error)

inline fun <T> ApiResponse<T>.fold(
    success: (T) -> Unit,
    empty: () -> Unit,
    error: (ApiError) -> Unit,
    failure: (ApiFailure) -> Unit
) = when (this) {
    is ApiResponse.Success -> success(data)
    is ApiResponse.Empty -> empty()
    is ApiResponse.Error -> error(this.error)
    is ApiResponse.Failure -> failure(this.error)
}


inline fun <T> ApiResponse<T>.fold(
    success: (T) -> Unit,
    fail: (Error) -> Unit,
    empty: () -> Unit
) = when (this) {
    is ApiResponse.Success -> success(data)
    is ApiResponse.Empty -> empty()
    is ApiResponse.Error -> fail(error)
    is ApiResponse.Failure -> fail(error)
}

inline fun <T> ApiResponse<T>.ifSuccessful(crossinline block: (T) -> Unit) {
    if (this is ApiResponse.Success) block(data)
}

inline fun <T> ApiResponse<T>.ifSuccessful(crossinline block: () -> Unit) {
    if (this is ApiResponse.Empty) block()
}

fun <T> ApiResponse<T>.getOrNull() = when (this) {
    is ApiResponse.Success -> data
    is ApiResponse.Empty,
    is ApiResponse.Error,
    is ApiResponse.Failure -> null
}