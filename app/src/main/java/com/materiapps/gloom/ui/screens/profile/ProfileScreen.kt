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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.materiapps.gloom.R
import com.materiapps.gloom.domain.models.ModelRepo
import com.materiapps.gloom.domain.models.ModelStatus
import com.materiapps.gloom.domain.models.ModelUser
import com.materiapps.gloom.domain.models.Pinnable
import com.materiapps.gloom.rest.dto.user.User
import com.materiapps.gloom.ui.components.BackButton
import com.materiapps.gloom.ui.components.RefreshIndicator
import com.materiapps.gloom.ui.screens.list.OrgsListScreen
import com.materiapps.gloom.ui.screens.list.RepositoryListScreen
import com.materiapps.gloom.ui.screens.list.SponsoringScreen
import com.materiapps.gloom.ui.screens.list.StarredReposListScreen
import com.materiapps.gloom.ui.viewmodels.profile.ProfileViewModel
import com.materiapps.gloom.ui.widgets.ReadMeCard
import com.materiapps.gloom.ui.widgets.repo.RepoItem
import com.materiapps.gloom.utils.EmojiUtils
import com.materiapps.gloom.utils.navigate
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
        val refreshState =
            rememberSwipeRefreshState(isRefreshing = viewModel.isLoading)

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { TopBar(viewModel, scrollBehavior) }
        ) {
            SwipeRefresh(
                state = refreshState,
                onRefresh = { viewModel.loadData() },
                indicator = { state, t -> RefreshIndicator(state, t) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(
                    Modifier
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (viewModel.user != null) {
                        Header(user = viewModel.user!!)

                        viewModel.user!!.readme?.let { readme ->
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                ReadMeCard(readme, viewModel.user!!.username ?: "ghost")
                            }
                        }

                        if (viewModel.user!!.pinnedItems.isNotEmpty()) PinnedItems(pinned = viewModel.user!!.pinnedItems)

                        StatCard(
                            isOrg = viewModel.user!!.type == User.Type.ORG,
                            repoCount = viewModel.user!!.repos ?: 0L,
                            orgCount = viewModel.user!!.orgs ?: 0L,
                            starCount = viewModel.user!!.starred ?: 0L,
                            sponsoringCount = viewModel.user!!.sponsoring ?: 0L,
                            username = viewModel.user!!.username
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                    } else {
                        Box(
                            Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (viewModel.hasErrors) {
                                //TODO: Show error indicator
                            }
                        }
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
            navigationIcon = { BackButton() },
            scrollBehavior = scrollBehavior
        )
    }

    @Composable
    private fun Header(
        user: ModelUser
    ) {
        val nav = LocalNavigator.current

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            AsyncImage(
                model = user.avatar,
                contentDescription = stringResource(
                    R.string.noun_users_avatar,
                    user.displayName ?: "ghost"
                ),
                modifier = Modifier
                    .size(90.dp)
                    .clip(if (user.type == User.Type.USER) CircleShape else RoundedCornerShape(28.dp))
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

            user.status?.let {
                Status(it)
            }

            user.bio?.let {
                if(it.isNotEmpty()) Text(
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
                            val url = if(site.startsWith("http://") || site.startsWith("https://")) site else "http://${site}"
                            it.openUri(url)
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

                if(user.type == User.Type.USER) Row {
                    TextButton(onClick = {
                        if (user.username?.isNotBlank() == true) nav?.navigate(FollowersScreen(user.username))
                    }) {
                        Text(stringResource(R.string.noun_follower_count, user.followers ?: 0))
                    }
                    TextButton(onClick = {
                        if (user.username?.isNotBlank() == true) nav?.navigate(FollowingScreen(user.username))
                    }) {
                        Text(stringResource(R.string.noun_following_count, user.following ?: 0))
                    }
                }
            }
        }
    }

    @Composable
    private fun Status(
        status: ModelStatus
    ) {
        if(status.emoji == null && status.message == null) return
        Box(modifier = Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
                    .padding(5.dp)
            ) {
                if(status.emoji != null)
                    AsyncImage(
                        model = EmojiUtils.emojis[status.emoji.replace(":", "")],
                        contentDescription = status.emoji,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                            .size(30.dp)
                            .padding(6.dp)
                    )

                if(status.message != null)
                    Text(
                        text = status.message,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
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
        isOrg: Boolean,
        repoCount: Long,
        orgCount: Long,
        starCount: Long,
        sponsoringCount: Long
    ) {
        val nav = LocalNavigator.current
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            ElevatedCard {
                Column {
                    StatItem(
                        label = stringResource(R.string.noun_repos),
                        count = repoCount,
                        icon = Icons.Outlined.Book
                    ) {
                        username?.let { RepositoryListScreen(it) }?.let { nav?.navigate(it) }
                    }
                    if (!isOrg) {
                        StatItem(
                            label = stringResource(R.string.noun_orgs),
                            count = orgCount,
                            icon = Icons.Outlined.Business
                        ) {
                            username?.let { OrgsListScreen(it) }?.let { nav?.navigate(it) }
                        }
                        StatItem(
                            label = stringResource(R.string.noun_starred),
                            count = starCount,
                            icon = Icons.Outlined.Star
                        ) {
                            username?.let { StarredReposListScreen(it) }?.let { nav?.navigate(it) }
                        }
                    }
                    if (sponsoringCount > 0) {
                        StatItem(
                            label = stringResource(R.string.noun_sponsoring),
                            count = sponsoringCount,
                            icon = Icons.Outlined.FavoriteBorder
                        ) {
                            username?.let { SponsoringScreen(it) }?.let { nav?.navigate(it) }
                        }
                    }
                }
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

    @Composable
    private fun PinnedItems(
        pinned: List<Pinnable?>
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                stringResource(R.string.title_pinned),
                style = MaterialTheme.typography.labelLarge,
                fontSize = 15.sp,
                modifier = Modifier.padding(horizontal = 18.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Spacer(Modifier)
                }
                items(pinned) {
                    when (it) {
                        is ModelRepo -> {
                            RepoItem(it, card = true)
                        }
                    }
                }
            }
        }
    }

}