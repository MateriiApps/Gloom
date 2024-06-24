package com.materiiapps.gloom.di.modules

import com.materiiapps.gloom.ui.viewmodels.auth.LandingViewModel
import com.materiiapps.gloom.ui.viewmodels.explorer.DirectoryListingViewModel
import com.materiiapps.gloom.ui.viewmodels.explorer.FileViewerViewModel
import com.materiiapps.gloom.ui.viewmodels.home.HomeViewModel
import com.materiiapps.gloom.ui.viewmodels.list.OrgListViewModel
import com.materiiapps.gloom.ui.viewmodels.list.RepositoryListViewModel
import com.materiiapps.gloom.ui.viewmodels.list.SponsoringViewModel
import com.materiiapps.gloom.ui.viewmodels.list.StarredReposListViewModel
import com.materiiapps.gloom.ui.viewmodels.profile.FollowersViewModel
import com.materiiapps.gloom.ui.viewmodels.profile.FollowingViewModel
import com.materiiapps.gloom.ui.viewmodels.profile.ProfileViewModel
import com.materiiapps.gloom.ui.viewmodels.release.ReleaseViewModel
import com.materiiapps.gloom.ui.viewmodels.repo.LicenseViewModel
import com.materiiapps.gloom.ui.viewmodels.repo.RepoViewModel
import com.materiiapps.gloom.ui.viewmodels.repo.tab.RepoCodeViewModel
import com.materiiapps.gloom.ui.viewmodels.repo.tab.RepoDetailsViewModel
import com.materiiapps.gloom.ui.viewmodels.repo.tab.RepoIssuesViewModel
import com.materiiapps.gloom.ui.viewmodels.repo.tab.RepoPullRequestsViewModel
import com.materiiapps.gloom.ui.viewmodels.repo.tab.RepoReleasesViewModel
import com.materiiapps.gloom.ui.viewmodels.settings.AccountSettingsViewModel
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
    factoryOf(::AccountSettingsViewModel)
    factoryOf(::HomeViewModel)

    factoryOf(::RepoViewModel)
    factoryOf(::RepoDetailsViewModel)
    factoryOf(::RepoCodeViewModel)
    factoryOf(::DirectoryListingViewModel)
    factoryOf(::FileViewerViewModel)
    factoryOf(::RepoIssuesViewModel)
    factoryOf(::RepoPullRequestsViewModel)
    factoryOf(::RepoReleasesViewModel)
    factoryOf(::ReleaseViewModel)
    factoryOf(::LicenseViewModel)

}