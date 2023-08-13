package com.materiiapps.gloom.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.components.LoadingButton
import com.materiiapps.gloom.ui.icons.GitHub
import com.materiiapps.gloom.ui.icons.Social
import com.materiiapps.gloom.ui.viewmodels.auth.LandingViewModel
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class LandingScreen : Screen {

    @Composable
    override fun Content() = Screen()

    @Composable
    private fun Screen() {
        val viewModel: LandingViewModel = getScreenModel()

        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.weight(0.20f))
                Image(
                    painter = painterResource(Res.images.gloom_logo),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
                Spacer(modifier = Modifier.weight(0.10f))
                Text(
                    text = stringResource(Res.strings.login_welcome),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Thin
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                LoadingButton(
                    loading = viewModel.authManager.loading,
                    onClick = { viewModel.signIn() }
                ) {
                    Icon(
                        Icons.Social.GitHub,
                        contentDescription = null
                    )
                    Text(stringResource(Res.strings.login_sign_in_github))
                }
                Spacer(Modifier.weight(0.20f))
            }
        }
    }

}