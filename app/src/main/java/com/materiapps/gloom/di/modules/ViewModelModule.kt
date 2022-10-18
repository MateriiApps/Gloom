package com.materiapps.gloom.di.modules

import com.materiapps.gloom.ui.viewmodels.auth.LandingViewModel
import com.materiapps.gloom.ui.viewmodels.list.RepositoryListViewModel
import com.materiapps.gloom.ui.viewmodels.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun viewModelModule() = module {

    viewModelOf(::LandingViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::RepositoryListViewModel)

}