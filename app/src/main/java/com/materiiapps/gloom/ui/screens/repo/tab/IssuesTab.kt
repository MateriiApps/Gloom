package com.materiiapps.gloom.ui.screens.repo.tab

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.materiiapps.gloom.R

class IssuesTab(): Tab {
    override val options: TabOptions
    @Composable get() = TabOptions(1u, stringResource(id = R.string.repo_tab_issues))

    @Composable
    override fun Content() = Screen()

    @Composable
    fun Screen() {

    }

}