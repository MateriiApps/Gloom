package com.materiiapps.gloom.ui.viewmodels.auth

import cafe.adriel.voyager.core.model.ScreenModel
import com.materiiapps.gloom.domain.manager.Account

expect class LandingViewModel : ScreenModel {

    fun signIn(type: Account.Type = Account.Type.REGULAR)

}