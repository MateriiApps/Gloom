package com.materiiapps.gloom.ui.screens.explorer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.materiiapps.gloom.ui.viewmodels.explorer.DirectoryListingViewModel
import org.koin.core.parameter.parametersOf
import java.util.UUID

class DirectoryListingScreen(
    private val owner: String,
    private val name: String,
    private val branchAndPath: String
) : Screen {

    override val key = "$owner/$name{$branchAndPath}-${UUID.randomUUID()}"

    @Composable
    override fun Content() = Screen()

    @Composable
    private fun Screen(
        viewModel: DirectoryListingViewModel = getScreenModel {
            parametersOf(DirectoryListingViewModel.Details(owner, name, branchAndPath))
        }
    ) {
        val nav = LocalNavigator.currentOrThrow

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            if(viewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 3.dp
                )
            }
            viewModel.entries.forEach { entry ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(22.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            if (entry.type == "tree")
                                nav.push(
                                    DirectoryListingScreen(
                                        owner,
                                        name,
                                        branchAndPath + "${entry.name}/"
                                    )
                                )
                        }
                        .padding(22.dp)
                        .fillMaxWidth()
                ) {
                    val (icon, description) = if (entry.type == "tree") Icons.Filled.Folder to "" else Icons.Outlined.Description to ""

                    Icon(icon, description)

                    Text(entry.name)
                }
            }
        }
    }
}