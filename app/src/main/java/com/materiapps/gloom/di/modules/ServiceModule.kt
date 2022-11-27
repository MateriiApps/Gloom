package com.materiapps.gloom.di.modules

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.network.http.LoggingInterceptor
import com.materiapps.gloom.BuildConfig
import com.materiapps.gloom.domain.manager.AuthManager
import com.materiapps.gloom.rest.service.GithubApiService
import com.materiapps.gloom.rest.service.GithubAuthApiService
import com.materiapps.gloom.rest.service.GraphQLService
import com.materiapps.gloom.rest.service.HttpService
import com.materiapps.gloom.utils.Logger
import com.materiapps.gloom.utils.URLs
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun serviceModule() = module {

    fun provideHttpService(json: Json, client: HttpClient) = HttpService(json, client)

    fun provideAuthApiService(httpService: HttpService): GithubAuthApiService = GithubAuthApiService(httpService)

    fun provideApiService(httpService: HttpService, authManager: AuthManager) = GithubApiService(httpService, authManager)

    fun provideApolloClient(logger: Logger): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(URLs.GRAPHQL)
            .addHttpInterceptor(LoggingInterceptor(LoggingInterceptor.Level.BODY) {
                if (BuildConfig.DEBUG) logger.debug("GraphQL", it)
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

    single {
        provideApolloClient(get())
    }

    singleOf(::GraphQLService)

}