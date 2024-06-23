package com.materiiapps.gloom.di.modules

import com.apollographql.apollo3.ApolloClient
import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.domain.manager.DialogManager
import com.materiiapps.gloom.domain.manager.DownloadManager
import com.materiiapps.gloom.domain.manager.LibraryManager
import com.materiiapps.gloom.domain.manager.PreferenceManager
import com.materiiapps.gloom.domain.manager.ShareManager
import com.materiiapps.gloom.domain.manager.ToastManager
import com.materiiapps.gloom.utils.Logger
import com.materiiapps.gloom.utils.SettingsProvider
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun managerModule() = module {

    singleOf(::ToastManager)
    singleOf(::DownloadManager)
    singleOf(::ShareManager)
    singleOf(::LibraryManager)

    fun providePreferenceManager(settings: SettingsProvider) = PreferenceManager(settings)
    fun provideDialogManager(settings: SettingsProvider) = DialogManager(settings)
    fun provideAuthManager(settings: SettingsProvider, apollo: ApolloClient, json: Json, logger: Logger) = AuthManager(settings, apollo, json, logger)

    single { providePreferenceManager(get(named("prefs"))) }
    single { provideDialogManager(get(named("dialogs"))) }
    single { provideAuthManager(get(named("auth")), get(), get(), get()) }

}