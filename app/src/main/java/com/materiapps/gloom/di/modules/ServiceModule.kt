package com.materiapps.gloom.di.modules

import com.apollographql.apollo3.ApolloClient
import com.materiapps.gloom.domain.manager.AuthManager
import com.materiapps.gloom.rest.service.GithubAuthApiService
import com.materiapps.gloom.utils.URLs
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun serviceModule() = module {

    single {
        GithubAuthApiService(get(named("Auth")))
    }

    single {
        val auth: AuthManager = get()
        ApolloClient.Builder()
            .serverUrl(URLs.GRAPHQL)
            .addHttpHeader("authorization", "Bearer ${auth.authToken}")
            .build()
    }

}