package com.materiapps.gloom.di.modules

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
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
            .addInterceptor(object : ApolloInterceptor {
                override fun <D : Operation.Data> intercept(
                    request: ApolloRequest<D>,
                    chain: ApolloInterceptorChain
                ): Flow<ApolloResponse<D>> {
                    val method =
                        if (request.operation.document().startsWith("query")) "GET" else "POST"
                    logger.debug(
                        "GraphQL",
                        "--> $method ${URLs.GRAPHQL}\n${request.operation.document()}"
                    )
                    return chain.proceed(request)
                }
            })
            .build()
    }

}