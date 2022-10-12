package com.materiapps.gloom.di.modules

import com.materiapps.gloom.domain.repository.GithubAuthRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun repositoryModule() = module {

    singleOf(::GithubAuthRepository)

}