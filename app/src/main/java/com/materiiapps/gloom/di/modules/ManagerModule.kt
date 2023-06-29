package com.materiiapps.gloom.di.modules

import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.domain.manager.DownloadManager
import com.materiiapps.gloom.domain.manager.PreferenceManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun managerModule() = module {

    singleOf(::PreferenceManager)
    singleOf(::AuthManager)
    singleOf(::DownloadManager)

}