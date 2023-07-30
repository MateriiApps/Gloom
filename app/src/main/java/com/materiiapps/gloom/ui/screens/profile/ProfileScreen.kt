package com.materiiapps.gloom.ui.screens.profile

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.PersonAddAlt
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
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
import com.materiiapps.gloom.R
import com.materiiapps.gloom.api.models.ModelRepo
import com.materiiapps.gloom.api.models.ModelStatus
import com.materiiapps.gloom.api.models.ModelUser
import com.materiiapps.gloom.api.models.Pinnable
import com.materiiapps.gloom.gql.type.SocialAccountProvider
import com.materiiapps.gloom.api.dto.user.User
import com.materiiapps.gloom.ui.components.Avatar
import com.materiiapps.gloom.ui.components.BackButton
import com.materiiapps.gloom.ui.components.BadgedItem
import com.materiiapps.gloom.ui.components.RefreshIndicator
import com.materiiapps.gloom.ui.screens.list.OrgsListScreen
import com.materiiapps.gloom.ui.screens.list.RepositoryListScreen
import com.materiiapps.gloom.ui.screens.list.SponsoringScreen
import com.materiiapps.gloom.ui.screens.list.StarredReposListScreen
import com.materiiapps.gloom.ui.screens.settings.SettingsScreen
import com.materiiapps.gloom.ui.viewmodels.profile.ProfileViewModel
import com.materiiapps.gloom.ui.widgets.ReadMeCard
import com.materiiapps.gloom.ui.widgets.profile.ContributionGraph
import com.materiiapps.gloom.ui.widgets.repo.RepoItem
import com.materiiapps.gloom.utils.Constants
import com.materiiapps.gloom.utils.EmojiUtils
import com.materiiapps.gloom.utils.navigate
import com.materiiapps.gloom.utils.shareText
import com.materiiapps.gloom.utils.showToast
import org.koin.core.parameter.parametersOf
import java.net.URI

open class ProfileScreen(
    val user: String = ""
) : Screen {

    override val key: ScreenKey
        get() = "${javaClass.name}($user)"

    @Composable
    override fun Content() = Screen()

    @Composable
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    private fun Screen(
        viewModel: ProfileViewModel = getScreenModel { parametersOf(user) }
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val refreshState =
            rememberPullRefreshState(viewModel.isLoading, onRefresh = { viewModel.loadData() })

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { TopBar(viewModel, scrollBehavior) }
        ) { pv ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pv)
                    .pullRefresh(refreshState)
            ) {
                Column(
                    Modifier
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    viewModel.user?.let { user ->
                        Header(user = user)

                        user.readme?.let { readme ->
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                ReadMeCard(readme, viewModel.user!!.username ?: "ghost")
                            }
                        }

                        if (user.pinnedItems.isNotEmpty()) PinnedItems(pinned = user.pinnedItems)

                        user.contributions?.let {
                            ContributionGraph(calendar = it.contributionCalendar)
                        }

                        StatCard(
                            isOrg = user.type == User.Type.ORG,
                            repoCount = user.repos ?: 0L,
                            orgCount = user.orgs ?: 0L,
                            starCount = user.starred ?: 0L,
                            sponsoringCount = user.sponsoring ?: 0L,
                            username = user.username
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                RefreshIndicator(refreshState, isRefreshing = viewModel.isLoading)
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun TopBar(
        viewModel: ProfileViewModel,
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        val nav = LocalNavigator.current
        val ctx = LocalContext.current

        TopAppBar(
            title = {
                val opacity = scrollBehavior.state.overlappedFraction
                val textColor by animateColorAsState(
                    targetValue = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = opacity
                    ),
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow), label = "Username fade"
                )
                Text(
                    text = viewModel.user?.username ?: "",
                    color = textColor
                )
            },
            navigationIcon = { BackButton() },
            scrollBehavior = scrollBehavior,
            actions = {
                if (!viewModel.user?.username.isNullOrBlank()) {
                    IconButton(onClick = { ctx.shareText("https://github.com/${viewModel.user!!.username}") }) {
                        Icon(Icons.Filled.Share, stringResource(R.string.action_share))
                    }
                }

                FollowButton(viewModel)

                if (user.isBlank()) {
                    IconButton(onClick = { nav?.navigate(SettingsScreen()) }) {
                        Icon(Icons.Outlined.Settings, stringResource(R.string.navigation_settings))
                    }
                }
            }
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
            ProfileAvatar(user)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                if (user.displayName != null) {
                    Text(
                        text = user.displayName!!,
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

            if(user.isFollowingYou) {
                Text(
                    text = stringResource(R.string.label_follows_you),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
                        .padding(horizontal = 9.dp, vertical = 5.dp)
                )
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
                        if (site == "null") return@let
                        ProfileDetail(
                            text = site,
                            icon = { Icon(Icons.Outlined.Link, contentDescription = null) }
                        ) {
                            val url =
                                if (site.startsWith("http://") || site.startsWith("https://")) site else "http://${site}"
                            it.openUri(url)
                        }
                    }

                    user.socials.forEach { social ->
                        val (icon, cdRes) = when (social.provider) {
                            SocialAccountProvider.TWITTER -> R.drawable.ic_twitter_24 to R.string.cd_twitter
                            SocialAccountProvider.YOUTUBE -> R.drawable.ic_youtube_24 to R.string.cd_youtube
                            SocialAccountProvider.MASTODON -> R.drawable.ic_mastodon_24 to R.string.cd_mastodon
                            SocialAccountProvider.HOMETOWN -> R.drawable.ic_hometown_24 to R.string.cd_hometown
                            SocialAccountProvider.FACEBOOK -> R.drawable.ic_facebook_24 to R.string.cd_facebook
                            SocialAccountProvider.INSTAGRAM -> R.drawable.ic_instagram_24 to R.string.cd_instagram
                            SocialAccountProvider.LINKEDIN -> R.drawable.ic_linkedin_24 to R.string.cd_linkedin
                            SocialAccountProvider.REDDIT -> R.drawable.ic_reddit_24 to R.string.cd_reddit
                            SocialAccountProvider.TWITCH -> R.drawable.ic_twitch_24 to R.string.cd_twitch
                            else -> R.drawable.ic_link_24 to R.string.cd_link
                        }
                        val socialName = remember {
                            if (social.provider == SocialAccountProvider.GENERIC) {
                                val link = URI.create(social.url)
                                "${link.authority.replaceFirst("www.", "")}${link.path}"
                            } else social.displayName
                        }

                        ProfileDetail(
                            text = socialName,
                            icon = {
                                Icon(
                                    painterResource(icon),
                                    contentDescription = stringResource(cdRes)
                                )
                            }
                        ) {
                            it.openUri(social.url)
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

                if(user.type == User.Type.USER) {
                    Row {
                        TextButton(onClick = {
                            if (user.username?.isNotBlank() == true) nav?.navigate(FollowersScreen(
                                user.username!!
                            ))
                        }) {
                            Text(stringResource(R.string.noun_follower_count, user.followers ?: 0))
                        }
                        TextButton(onClick = {
                            if (user.username?.isNotBlank() == true) nav?.navigate(FollowingScreen(
                                user.username!!
                            ))
                        }) {
                            Text(stringResource(R.string.noun_following_count, user.following ?: 0))
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ProfileAvatar(user: ModelUser) {
        val ctx = LocalContext.current

        val (badge, msg) =
            if (user.isSupporter) painterResource(R.drawable.img_badge_sponsor) to R.string.badge_supporter
            else if (user.id == Constants.DEV_USER_ID) painterResource(R.drawable.img_badge_dev) to R.string.badge_dev
            else null to null

        BadgedItem(badge = if (badge != null && msg != null) { ->
            Image(
                painter = badge,
                contentDescription = stringResource(msg),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { ctx.showToast(ctx.getString(msg)) }
            )
        } else null) {
            Avatar(
                url = user.avatar,
                contentDescription = stringResource(
                    R.string.noun_users_avatar,
                    user.displayName ?: "ghost"
                ),
                type = user.type,
                modifier = Modifier.size(90.dp)
            )
        }
    }

    @Composable
    private fun Status(
        status: ModelStatus
    ) {
        if (status.emoji == null && status.message == null) return
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
                        model = EmojiUtils.emojis[status.emoji!!.replace(":", "")],
                        contentDescription = status.emoji,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                            .size(30.dp)
                            .padding(6.dp)
                    )

                if(status.message != null)
                    Text(
                        text = status.message!!,
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

    @Composable
    fun FollowButton(
        viewModel: ProfileViewModel
    ) {
        val (icon, alt) = if (viewModel.user?.isFollowing == true)
            Icons.Filled.HowToReg to stringResource(
                R.string.action_unfollow_user,
                viewModel.user?.username ?: "ghost"
            )
        else Icons.Outlined.PersonAddAlt to stringResource(
            R.string.action_follow_user,
            viewModel.user?.username ?: "ghost"
        )

        if (viewModel.user?.canFollow == true) IconButton(onClick = { viewModel.toggleFollowing() }) {
            Icon(icon, contentDescription = alt)
        }
    }

}