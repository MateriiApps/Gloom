package com.materiapps.gloom.di.modules

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import com.apollographql.apollo3.network.http.LoggingInterceptor
import com.materiapps.gloom.BuildConfig
import com.materiapps.gloom.rest.service.GithubApiService
import com.materiapps.gloom.rest.service.GithubAuthApiService
import com.materiapps.gloom.rest.service.HttpService
import com.materiapps.gloom.utils.Logger
import com.materiapps.gloom.utils.URLs
import kotlinx.coroutines.flow.Flow
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun serviceModule() = module {

    single(named("Auth")) {
        HttpService(get(), get(named("Auth")))
    }

    single(named("Rest")) {
        HttpService(get(), get(named("Rest")), get())
    }

    single {
        GithubAuthApiService(get(named("Auth")))
    }

    single {
        GithubApiService(get(named("Rest")))
    }

    single {
        val logger: Logger = get()

        ApolloClient.Builder()
            .serverUrl(URLs.GRAPHQL)
            .addHttpInterceptor(LoggingInterceptor(LoggingInterceptor.Level.BODY) {
                if(BuildConfig.DEBUG) logger.debug("GraphQL", it)
            })
            .build()
    }

}