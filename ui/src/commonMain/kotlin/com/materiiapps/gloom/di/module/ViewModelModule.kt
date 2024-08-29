package com.materiiapps.gloom.di.module

import com.materiiapps.gloom.ui.screen.auth.viewmodel.LandingViewModel
import com.materiiapps.gloom.ui.screen.explorer.viewmodel.DirectoryListingViewModel
import com.materiiapps.gloom.ui.screen.explorer.viewmodel.FileViewerViewModel
import com.materiiapps.gloom.ui.screen.home.viewmodel.HomeViewModel
import com.materiiapps.gloom.ui.screen.list.viewmodel.OrgListViewModel
import com.materiiapps.gloom.ui.screen.list.viewmodel.RepositoryListViewModel
import com.materiiapps.gloom.ui.screen.list.viewmodel.SponsoringViewModel
import com.materiiapps.gloom.ui.screen.list.viewmodel.StarredReposListViewModel
import com.materiiapps.gloom.ui.screen.profile.viewmodel.FollowersViewModel
import com.materiiapps.gloom.ui.screen.profile.viewmodel.FollowingViewModel
import com.materiiapps.gloom.ui.screen.profile.viewmodel.ProfileViewModel
import com.materiiapps.gloom.ui.screen.repo.viewmodel.LicenseViewModel
import com.materiiapps.gloom.ui.screen.repo.viewmodel.RepoViewModel
import com.materiiapps.gloom.ui.screen.repo.viewmodel.RepoCodeViewModel
import com.materiiapps.gloom.ui.screen.repo.viewmodel.RepoDetailsViewModel
import com.materiiapps.gloom.ui.screen.repo.viewmodel.RepoIssuesViewModel
import com.materiiapps.gloom.ui.screen.repo.viewmodel.RepoPullRequestsViewModel
import com.materiiapps.gloom.ui.screen.repo.viewmodel.RepoReleasesViewModel
import com.materiiapps.gloom.ui.screen.settings.viewmodel.AccountSettingsViewModel
import com.materiiapps.gloom.ui.screen.settings.viewmodel.AppearanceSettingsViewModel
import com.materiiapps.gloom.ui.screen.settings.viewmodel.SettingsViewModel
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
    factoryOf(::LicenseViewModel)

}