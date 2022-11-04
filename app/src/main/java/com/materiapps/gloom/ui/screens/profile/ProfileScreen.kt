package com.materiapps.gloom.ui.screens.profile

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Pin
import androidx.compose.material.icons.outlined.PinDrop
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.materiapps.gloom.R
import com.materiapps.gloom.domain.models.ModelUser
import com.materiapps.gloom.ui.screens.list.RepositoryListScreen
import com.materiapps.gloom.ui.viewmodels.profile.ProfileViewModel
import com.materiapps.gloom.ui.widgets.ReadMeCard
import com.materiapps.gloom.utils.navigate
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

open class ProfileScreen(
    val user: String = ""
) : Screen {

    override val key: ScreenKey
        get() = "${javaClass.name}($user)"

    @Composable
    override fun Content() = Screen()

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Screen(
        viewModel: ProfileViewModel = getScreenModel { parametersOf(user) }
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
                        ReadMeCard(viewModel.readMe, viewModel.user!!.username ?: "ghost")

                    StatCard(
                        repoCount = viewModel.user!!.repos ?: 0L,
                        orgCount = viewModel.user!!.orgs ?: 0L,
                        starCount = viewModel.user!!.starred ?: 0L,
                        username = viewModel.user!!.username
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
                    text = viewModel.user?.username ?: "",
                    color = textColor
                )
            },
            scrollBehavior = scrollBehavior
        )
    }

    @Composable
    private fun Header(
        user: ModelUser
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = user.avatar,
                contentDescription = "${user.displayName}'s avatar",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                if (user.displayName != null) {
                    Text(
                        text = user.displayName,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = user.username ?: "ghost",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    )
                } else {
                    Text(
                        text = user.username ?: "ghost",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            user.bio?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FlowRow(
                    mainAxisAlignment = FlowMainAxisAlignment.Center,
                    mainAxisSpacing = 5.dp
                ) {
                    user.company?.let {
                        ProfileDetail(
                            text = it,
                            icon = { Icon(Icons.Outlined.Business, contentDescription = null) }
                        )
                    }

                    user.location?.let {
                        ProfileDetail(
                            text = it,
                            icon = { Icon(Icons.Outlined.Place, contentDescription = null) }
                        )
                    }

                    user.website?.let { site ->
                        if(site == "null") return@let
                        ProfileDetail(
                            text = site,
                            icon = { Icon(Icons.Outlined.Link, contentDescription = null) }
                        ) {
                            it.openUri("http://$site")
                        }
                    }

                    user.twitterUsername?.let { handle ->
                        ProfileDetail(
                            text = "@${handle}",
                            icon = { Icon(painterResource(R.drawable.ic_twitter_24), contentDescription = null) }
                        ) {
                            it.openUri("https://twitter.com/$handle")
                        }
                    }

                    user.email?.let { email ->
                        if(email.isBlank()) return@let
                        ProfileDetail(
                            text = email,
                            icon = { Icon(Icons.Outlined.Email, contentDescription = null) }
                        ) {
                            it.openUri("mailto:$email")
                        }
                    }
                }

                Row {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "${user.followers} Followers")
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "${user.following} Following")
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
        repoCount: Long,
        orgCount: Long,
        starCount: Long
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
        count: Long,
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