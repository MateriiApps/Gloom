package com.materiiapps.gloom.ui.screens.repo

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.components.BackButton
import com.materiiapps.gloom.ui.components.NavBarSpacer
import com.materiiapps.gloom.ui.viewmodels.repo.LicenseViewModel
import com.materiiapps.gloom.ui.widgets.repo.LicenseDetails
import dev.icerock.moko.resources.compose.stringResource
import org.koin.core.parameter.parametersOf

class LicenseScreen(
    private val owner: String,
    private val name: String
): Screen {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val viewModel: LicenseViewModel = getScreenModel { parametersOf(owner to name) }
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            topBar = { TitleBar(scrollBehavior) },
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { pv ->
            Column(
                modifier = Modifier
                    .padding(pv)
                    .verticalScroll(rememberScrollState())
            ) {
                if (viewModel.isLoading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }

                viewModel.license?.let { license ->
                    if (!license.pseudoLicense) {
                        Box(modifier = Modifier.padding(16.dp)) {
                            LicenseDetails(license)
                        }
                    }
                }

                viewModel.licenseText?.let { licenseBody ->
                    Text(
                        text = licenseBody,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 1.1.sp,
                        modifier = Modifier
                            .padding(16.dp)
                            .horizontalScroll(rememberScrollState())
                    )
                }

                NavBarSpacer()
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun TitleBar(
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        buildAnnotatedString {
                            append(owner)
                            withStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                        0.5f
                                    )
                                )
                            ) {
                                append(" / ")
                            }
                            append(name)
                        },
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(stringResource(Res.strings.noun_license))
                }
            },
            navigationIcon = {
                BackButton()
            },
            scrollBehavior = scrollBehavior
        )
    }

}