package com.materiiapps.gloom.di

import com.materiiapps.gloom.api.repository.GithubAuthRepository
import com.materiiapps.gloom.api.repository.GithubRepository
import com.materiiapps.gloom.api.repository.GraphQLRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun repositoryModule() = module {

    singleOf(::GithubAuthRepository)
    singleOf(::GithubRepository)
    singleOf(::GraphQLRepository)

}