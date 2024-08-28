package com.materiiapps.gloom.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.network.http.LoggingInterceptor
import com.materiiapps.gloom.api.URLs
import com.materiiapps.gloom.api.service.GithubApiService
import com.materiiapps.gloom.api.service.GithubAuthApiService
import com.materiiapps.gloom.api.service.GraphQLService
import com.materiiapps.gloom.api.service.HttpService
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.utils.Logger
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun serviceModule() = module {

    fun provideHttpService(json: Json, client: HttpClient) = HttpService(json, client)

    fun provideAuthApiService(httpService: HttpService): GithubAuthApiService =
        GithubAuthApiService(httpService)

    fun provideApiService(httpService: HttpService, authManager: AuthManager) =
        GithubApiService(httpService, authManager)

    fun provideApolloClient(logger: Logger): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(URLs.GRAPHQL)
            .addHttpInterceptor(LoggingInterceptor(LoggingInterceptor.Level.BODY) {
                logger.debug("GraphQL", it)
            })
            .normalizedCache(MemoryCacheFactory(10 * 1024 * 1024, 1000 * 30))
            .build()
    }

    single(named("Auth")) {
        provideHttpService(get(), get(named("Auth")))
    }

    single(named("Rest")) {
        provideHttpService(get(), get(named("Rest")))
    }

    single {
        provideAuthApiService(get(named("Auth")))
    }

    single {
        provideApiService(get(named("Rest")), get())
    }

    singleOf(::provideApolloClient)
    singleOf(::GraphQLService)

}