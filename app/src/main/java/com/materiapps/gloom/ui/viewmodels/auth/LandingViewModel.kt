package com.materiapps.gloom.ui.viewmodels.auth

import android.content.Context
import cafe.adriel.voyager.core.model.ScreenModel
import com.materiapps.gloom.BuildConfig
import com.materiapps.gloom.utils.URLs
import com.materiapps.gloom.utils.openCustomTab
import io.ktor.http.URLBuilder

class LandingViewModel : ScreenModel {

    fun signIn(context: Context) {
        val url = URLBuilder(URLs.AUTH.LOGIN).also {
            it.parameters.apply {
                append("client_id", BuildConfig.CLIENT_ID)
                append("redirect_uri", "gloom://oauth")
            }
        }.buildString()

        context.openCustomTab(url, force = true)
    }

}