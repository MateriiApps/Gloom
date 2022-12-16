package com.materiapps.gloom.ui.screens.repo.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
<<<<<<< HEAD
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.materiapps.gloom.R

class IssuesTab(): Tab {
    override val options: TabOptions
    @Composable get() = TabOptions(1u, stringResource(id = R.string.repo_tab_issues))

    @Composable
    override fun Content() = Screen()

    @Composable
    fun Screen() {

    }

=======

@Composable
fun IssuesTab() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Issues")
    }
>>>>>>> 430f7f6 (Setup and details tab)
}