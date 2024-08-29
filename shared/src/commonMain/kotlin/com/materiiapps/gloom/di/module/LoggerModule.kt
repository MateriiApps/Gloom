package com.materiiapps.gloom.di.module

import com.materiiapps.gloom.util.Logger
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun loggerModule() = module {

    singleOf(::Logger)

}