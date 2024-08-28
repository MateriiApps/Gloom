package com.materiiapps.gloom.ui.screen.explorer

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
import com.benasher44.uuid.uuid4
import com.materiiapps.gloom.ui.utils.navigate
import com.materiiapps.gloom.ui.screen.explorer.viewmodel.DirectoryListingViewModel
import org.koin.core.parameter.parametersOf

class DirectoryListingScreen(
    private val owner: String,
    private val name: String,
    private val branchAndPath: String
) : Screen {

    override val key = "$owner/$name{$branchAndPath}-${uuid4()}"

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
            if (viewModel.isLoading) {
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
                            when (entry.type) {
                                "tree" -> nav.push(
                                    DirectoryListingScreen(
                                        owner,
                                        name,
                                        branchAndPath + "${entry.name}/"
                                    )
                                )

                                "blob" -> {
                                    val (branch, path) = branchAndPath.split(":")
                                    nav.navigate(
                                        FileViewerScreen(
                                            owner,
                                            name,
                                            branch,
                                            "$path${entry.name}"
                                        )
                                    )
                                }
                            }

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