package com.materiapps.gloom.di.modules

import com.materiapps.gloom.utils.Logger
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun loggerModule() = module {

    singleOf(::Logger)

}