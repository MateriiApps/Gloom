package com.materiiapps.gloom.di.modules

import com.materiiapps.gloom.ui.viewmodels.auth.LandingViewModel
import com.materiiapps.gloom.ui.viewmodels.home.HomeViewModel
import com.materiiapps.gloom.ui.viewmodels.list.OrgListViewModel
import com.materiiapps.gloom.ui.viewmodels.list.RepositoryListViewModel
import com.materiiapps.gloom.ui.viewmodels.list.SponsoringViewModel
import com.materiiapps.gloom.ui.viewmodels.list.StarredReposListViewModel
import com.materiiapps.gloom.ui.viewmodels.profile.FollowersViewModel
import com.materiiapps.gloom.ui.viewmodels.profile.FollowingViewModel
import com.materiiapps.gloom.ui.viewmodels.profile.ProfileViewModel
import com.materiiapps.gloom.ui.viewmodels.settings.AppearanceSettingsViewModel
import com.materiiapps.gloom.ui.viewmodels.settings.SettingsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun viewModelModule() = module {

    factoryOf(::LandingViewModel)
    factoryOf(::ProfileViewModel)
    factoryOf(::RepositoryListViewModel)
    factoryOf(::StarredReposListViewModel)
    factoryOf(::OrgListViewModel)
    factoryOf(::FollowersViewModel)
    factoryOf(::FollowingViewModel)
    factoryOf(::SponsoringViewModel)
    factoryOf(::SettingsViewModel)
    factoryOf(::AppearanceSettingsViewModel)
    factoryOf(::HomeViewModel)

}