package com.materiiapps.gloom.di.modules

import com.materiiapps.gloom.domain.repository.GithubAuthRepository
import com.materiiapps.gloom.domain.repository.GithubRepository
import com.materiiapps.gloom.domain.repository.GraphQLRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun repositoryModule() = module {

    singleOf(::GithubAuthRepository)
    singleOf(::GithubRepository)
    singleOf(::GraphQLRepository)

}