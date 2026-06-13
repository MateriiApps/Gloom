package dev.materii.gloom.core.data.di

import dev.materii.gloom.core.data.repository.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val RepositoryModule = module {

    singleOf(::AccountRepositoryImpl) bind AccountRepository::class
    singleOf(::FeedRepositoryImpl) bind FeedRepository::class
    singleOf(::FilesRepositoryImpl) bind FilesRepository::class
    singleOf(::ProfileRepositoryImpl) bind ProfileRepository::class
    singleOf(::ReleaseRepositoryImpl) bind ReleaseRepository::class
    singleOf(::RepoRepositoryImpl) bind RepoRepository::class
    singleOf(::TrendingRepositoryImpl) bind TrendingRepository::class

}