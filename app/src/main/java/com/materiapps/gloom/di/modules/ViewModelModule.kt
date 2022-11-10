package com.materiapps.gloom.di.modules

import com.materiapps.gloom.ui.viewmodels.auth.LandingViewModel
import com.materiapps.gloom.ui.viewmodels.list.OrgListViewModel
import com.materiapps.gloom.ui.viewmodels.list.RepositoryListViewModel
import com.materiapps.gloom.ui.viewmodels.list.StarredReposListViewModel
import com.materiapps.gloom.ui.viewmodels.profile.FollowersViewModel
import com.materiapps.gloom.ui.viewmodels.profile.FollowingViewModel
import com.materiapps.gloom.ui.viewmodels.profile.ProfileViewModel
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

}