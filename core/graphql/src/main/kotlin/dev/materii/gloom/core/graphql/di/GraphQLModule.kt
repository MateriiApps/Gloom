package dev.materii.gloom.core.graphql.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.network.http.LoggingInterceptor
import dev.materii.gloom.core.graphql.GraphQLDataSource
import dev.materii.gloom.core.graphql.NetworkGraphQLDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val GraphQLModule = module {

    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://api.github.com/graphql")
            .addHttpInterceptor(LoggingInterceptor(LoggingInterceptor.Level.BODY) {
//                if (BuildConfig.DEBUG) Log.d("GraphQL", it)
            })
            .normalizedCache(MemoryCacheFactory(10 * 1024 * 1024, 1000 * 30))
            .build()
    }

    singleOf(::provideApolloClient)
    singleOf(::NetworkGraphQLDataSource) bind GraphQLDataSource::class

}