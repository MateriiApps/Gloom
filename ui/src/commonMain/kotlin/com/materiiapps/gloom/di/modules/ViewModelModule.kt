package com.materiiapps.gloom.di.modules

import com.materiiapps.gloom.ui.screens.auth.viewmodel.LandingViewModel
import com.materiiapps.gloom.ui.screens.explorer.viewmodel.DirectoryListingViewModel
import com.materiiapps.gloom.ui.screens.explorer.viewmodel.FileViewerViewModel
import com.materiiapps.gloom.ui.screens.home.viewmodel.HomeViewModel
import com.materiiapps.gloom.ui.screens.list.viewmodel.OrgListViewModel
import com.materiiapps.gloom.ui.screens.list.viewmodel.RepositoryListViewModel
import com.materiiapps.gloom.ui.screens.list.viewmodel.SponsoringViewModel
import com.materiiapps.gloom.ui.screens.list.viewmodel.StarredReposListViewModel
import com.materiiapps.gloom.ui.screens.profile.viewmodel.FollowersViewModel
import com.materiiapps.gloom.ui.screens.profile.viewmodel.FollowingViewModel
import com.materiiapps.gloom.ui.screens.profile.viewmodel.ProfileViewModel
import com.materiiapps.gloom.ui.screens.repo.viewmodel.LicenseViewModel
import com.materiiapps.gloom.ui.screens.repo.viewmodel.RepoViewModel
import com.materiiapps.gloom.ui.screens.repo.viewmodel.RepoCodeViewModel
import com.materiiapps.gloom.ui.screens.repo.viewmodel.RepoDetailsViewModel
import com.materiiapps.gloom.ui.screens.repo.viewmodel.RepoIssuesViewModel
import com.materiiapps.gloom.ui.screens.repo.viewmodel.RepoPullRequestsViewModel
import com.materiiapps.gloom.ui.screens.repo.viewmodel.RepoReleasesViewModel
import com.materiiapps.gloom.ui.screens.settings.viewmodel.AccountSettingsViewModel
import com.materiiapps.gloom.ui.screens.settings.viewmodel.AppearanceSettingsViewModel
import com.materiiapps.gloom.ui.screens.settings.viewmodel.SettingsViewModel
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