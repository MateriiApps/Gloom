package com.materiapps.gloom.di.modules

import com.materiapps.gloom.ui.viewmodels.auth.LandingViewModel
import com.materiapps.gloom.ui.viewmodels.explorer.DirectoryListingViewModel
import com.materiapps.gloom.ui.viewmodels.home.HomeViewModel
import com.materiapps.gloom.ui.viewmodels.list.OrgListViewModel
import com.materiapps.gloom.ui.viewmodels.list.RepositoryListViewModel
import com.materiapps.gloom.ui.viewmodels.list.SponsoringViewModel
import com.materiapps.gloom.ui.viewmodels.list.StarredReposListViewModel
import com.materiapps.gloom.ui.viewmodels.profile.FollowersViewModel
import com.materiapps.gloom.ui.viewmodels.profile.FollowingViewModel
import com.materiapps.gloom.ui.viewmodels.profile.ProfileViewModel
import com.materiapps.gloom.ui.viewmodels.repo.RepoViewModel
import com.materiapps.gloom.ui.viewmodels.repo.tab.RepoCodeViewModel
import com.materiapps.gloom.ui.viewmodels.repo.tab.RepoDetailsViewModel
import com.materiapps.gloom.ui.viewmodels.settings.AppearanceSettingsViewModel
import com.materiapps.gloom.ui.viewmodels.settings.SettingsViewModel
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

    factoryOf(::RepoViewModel)
    factoryOf(::RepoDetailsViewModel)
    factoryOf(::RepoCodeViewModel)
    factoryOf(::DirectoryListingViewModel)

}