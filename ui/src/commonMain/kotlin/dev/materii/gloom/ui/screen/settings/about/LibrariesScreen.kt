package dev.materii.gloom.ui.screen.settings.about

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
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.domain.manager.LibraryManager
import dev.materii.gloom.ui.component.ThinDivider
import dev.materii.gloom.ui.component.toolbar.LargeToolbar
import dev.materii.gloom.ui.screen.settings.component.about.LibraryItem
import org.koin.compose.koinInject

class LibrariesScreen: Screen {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val libraryManager: LibraryManager = koinInject()
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
                    if (i != libraryManager.libs.libraries.lastIndex) {
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