package com.materiiapps.gloom.ui.screens.settings.about

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import cafe.adriel.voyager.core.screen.Screen
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.domain.manager.LibraryManager
import com.materiiapps.gloom.ui.components.LargeToolbar
import com.materiiapps.gloom.ui.components.ThinDivider
import com.materiiapps.gloom.ui.widgets.about.LibraryItem
import dev.icerock.moko.resources.compose.stringResource
import org.koin.androidx.compose.get

class LibrariesScreen: Screen {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val libraryManager: LibraryManager = get()
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        Scaffold(
            topBar = { Toolbar(scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { pv ->
            LazyColumn(
                modifier = Modifier.padding(pv)
            ) {
                itemsIndexed(libraryManager.libs.libraries) { i, library ->
                    LibraryItem(
                        library = library
                    )
                    if(i != libraryManager.libs.libraries.lastIndex) {
                        ThinDivider()
                    }
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Toolbar(
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        LargeToolbar(
            title = stringResource(Res.strings.settings_libraries),
            scrollBehavior = scrollBehavior
        )
    }

}