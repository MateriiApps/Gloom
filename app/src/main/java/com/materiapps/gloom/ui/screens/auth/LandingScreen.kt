package com.materiapps.gloom.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.materiapps.gloom.R
import com.materiapps.gloom.ui.viewmodels.auth.LandingViewModel

class LandingScreen : Screen {

    @Composable
    override fun Content() = Screen()

    @Composable
    private fun Screen(
        viewModel: LandingViewModel = getScreenModel()
    ) {
        val ctx = LocalContext.current
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.weight(0.20f))
                Image(
                    painter = painterResource(R.drawable.ic_gloom),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
                Spacer(modifier = Modifier.weight(0.10f))
                Text(
                    text = stringResource(R.string.login_welcome),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Thin
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Button(onClick = { viewModel.signIn(ctx) }) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(ButtonDefaults.IconSpacing),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_github_24),
                            contentDescription = null
                        )
                        Text(stringResource(R.string.login_sign_in_github))
                    }
                }
                Spacer(Modifier.weight(0.20f))
            }
        }
    }

}