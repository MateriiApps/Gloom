package dev.materii.gloom.core.graphql.util

import com.apollographql.apollo.api.Optional

fun <T> T?.toOptional(): Optional<T> = Optional.presentIfNotNull(this)