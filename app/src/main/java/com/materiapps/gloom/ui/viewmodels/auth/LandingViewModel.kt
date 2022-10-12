package com.materiapps.gloom.ui.viewmodels.auth

import android.content.Context
import cafe.adriel.voyager.core.model.ScreenModel
import com.materiapps.gloom.BuildConfig
import com.materiapps.gloom.utils.URLs
import com.materiapps.gloom.utils.openUrl
import io.ktor.http.*

class LandingViewModel(
    private val context: Context
) : ScreenModel {

    fun signIn() {
        val url = URLBuilder(URLs.AUTH.LOGIN).also {
            it.parameters.apply {
                append("client_id", BuildConfig.CLIENT_ID)
                append("redirect_uri", "gloom://oauth")
            }
        }.buildString()

        context.openUrl(url)
    }

}