package com.materiapps.gloom.ui.screens.profile

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.materiapps.gloom.ProfileQuery
import com.materiapps.gloom.R
import com.materiapps.gloom.ui.screens.list.RepositoryListScreen
import com.materiapps.gloom.ui.viewmodels.profile.ProfileViewModel
import com.materiapps.gloom.ui.widgets.ReadMeCard
import com.materiapps.gloom.utils.navigate

class ProfileScreen(
    val user: String = ""
) : Tab {
    override val options: TabOptions
        @Composable get() {
            val navigator = LocalTabNavigator.current
            val selected = navigator.current == this
            return TabOptions(
                0u,
                "Profile",
                rememberVectorPainter(if (selected) Icons.Filled.Person else Icons.Outlined.Person)
            )
        }

    @Composable
    override fun Content() = Screen()

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Screen(
        viewModel: ProfileViewModel = getScreenModel()
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { TopBar(viewModel, scrollBehavior) }
        ) {
            Column(
                Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (viewModel.user != null) {
                    Header(user = viewModel.user!!)

                    if (viewModel.readMe.isNotEmpty())
                        ReadMeCard(viewModel.readMe)

                    StatCard(
                        repoCount = viewModel.user!!.repositories.totalCount,
                        orgCount = viewModel.user!!.organizations.totalCount,
                        starCount = viewModel.user!!.starredRepositories.totalCount,
                        username = viewModel.user!!.login
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                } else {
                    Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun TopBar(
        viewModel: ProfileViewModel,
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        TopAppBar(
            title = {
                val opacity = scrollBehavior.state.overlappedFraction
                val textColor by animateColorAsState(
                    targetValue = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = opacity
                    ),
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                )
                Text(
                    text = viewModel.user?.login ?: "",
                    color = textColor
                )
            },
            scrollBehavior = scrollBehavior
        )
    }

    @Composable
    private fun Header(
        user: ProfileQuery.Viewer
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = "${user.name}'s avatar",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                if (user.name != null) {
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = user.login,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    )
                } else {
                    Text(
                        text = user.login,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            Text(
                text = user.bio ?: ""
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FlowRow(
                    mainAxisAlignment = FlowMainAxisAlignment.Center,
                    mainAxisSpacing = 5.dp
                ) {
                    if (user.company != null) ProfileDetail(
                        text = user.company,
                        icon = { Icon(Icons.Outlined.Business, contentDescription = null) }
                    )
                    if (user.websiteUrl != null) ProfileDetail(
                        text = user.websiteUrl.toString(),
                        icon = { Icon(Icons.Outlined.Link, contentDescription = null) }
                    ) {
                        it.openUri("http://${user.websiteUrl}")
                    }
                    if (user.twitterUsername != null) ProfileDetail(
                        text = "@${user.twitterUsername}",
                        icon = { Icon(painterResource(R.drawable.ic_twitter_24), contentDescription = null) }
                    ) {
                        it.openUri("https://twitter.com/${user.twitterUsername}")
                    }
                }

                Row {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "${user.followers.totalCount} Followers")
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "${user.following.totalCount} Following")
                    }
                }
            }
        }
    }

    @Composable
    private fun ProfileDetail(
        text: String,
        icon: @Composable (() -> Unit)? = null,
        onClick: (UriHandler) -> Unit = {}
    ) {
        val uriHandler = LocalUriHandler.current
        val contentColor = MaterialTheme.colorScheme.primary

        Surface(
            contentColor = contentColor,
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    onClick(uriHandler)
                }
        ) {
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
                    Row(
                        Modifier
                            .padding(vertical = 5.dp, horizontal = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        icon?.invoke()
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text)
                    }
                }
            }
        }
    }

    @Composable
    private fun StatCard(
        username: String?,
        repoCount: Int,
        orgCount: Int,
        starCount: Int
    ) {
        val nav = LocalNavigator.current

        ElevatedCard {
            Column {
                StatItem(
                    label = stringResource(R.string.repos),
                    count = repoCount,
                    icon = Icons.Outlined.Book
                ) {
                    username?.let { RepositoryListScreen(it) }?.let { nav?.navigate(it) }
                }
                StatItem(
                    label = "Organizations",
                    count = orgCount,
                    icon = Icons.Outlined.Business
                )
                StatItem(
                    label = "Starred",
                    count = starCount,
                    icon = Icons.Outlined.Star
                )
            }
        }
    }

    @Composable
    private fun StatItem(
        label: String,
        count: Int,
        icon: ImageVector,
        onClick: (() -> Unit)? = null
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onClick?.invoke() }
                .padding(14.dp)
        ) {
            Icon(
                icon, null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(15.dp))
                    .size(32.dp)
                    .padding(6.dp)
            )
            Text(text = label)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = count.toString(),
                color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
            )
        }
    }

}