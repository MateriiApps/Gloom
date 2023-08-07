package com.materiiapps.gloom.di.modules

import com.materiiapps.gloom.domain.manager.AuthManager
import com.materiiapps.gloom.domain.manager.DialogManager
import com.materiiapps.gloom.domain.manager.DownloadManager
import com.materiiapps.gloom.domain.manager.PreferenceManager
import com.materiiapps.gloom.domain.manager.ShareManager
import com.materiiapps.gloom.domain.manager.ToastManager
import com.materiiapps.gloom.utils.SettingsProvider
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun managerModule() = module {

    singleOf(::AuthManager)
    singleOf(::DownloadManager)
    singleOf(::ShareManager)
    singleOf(::ToastManager)

    fun providePreferenceManager(settings: SettingsProvider) = PreferenceManager(settings)
    fun provideDialogManager(settings: SettingsProvider) = DialogManager(settings)

    single { providePreferenceManager(get(named("prefs"))) }
    single { provideDialogManager(get(named("dialogs"))) }

}