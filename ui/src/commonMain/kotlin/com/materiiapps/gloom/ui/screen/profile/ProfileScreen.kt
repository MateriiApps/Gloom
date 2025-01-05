package com.materiiapps.gloom.ui.screen.profile

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.api.dto.user.User
import com.materiiapps.gloom.api.model.ModelRepo
import com.materiiapps.gloom.api.model.ModelStatus
import com.materiiapps.gloom.api.model.ModelUser
import com.materiiapps.gloom.api.model.Pinnable
import com.materiiapps.gloom.domain.manager.ShareManager
import com.materiiapps.gloom.gql.type.SocialAccountProvider
import com.materiiapps.gloom.ui.component.Avatar
import com.materiiapps.gloom.ui.component.BackButton
import com.materiiapps.gloom.ui.component.BadgedItem
import com.materiiapps.gloom.ui.icon.Social
import com.materiiapps.gloom.ui.icon.social.Bluesky
import com.materiiapps.gloom.ui.icon.social.Facebook
import com.materiiapps.gloom.ui.icon.social.Hometown
import com.materiiapps.gloom.ui.icon.social.Instagram
import com.materiiapps.gloom.ui.icon.social.LinkedIn
import com.materiiapps.gloom.ui.icon.social.Mastodon
import com.materiiapps.gloom.ui.icon.social.NPM
import com.materiiapps.gloom.ui.icon.social.Reddit
import com.materiiapps.gloom.ui.icon.social.Twitch
import com.materiiapps.gloom.ui.icon.social.Twitter
import com.materiiapps.gloom.ui.icon.social.YouTube
import com.materiiapps.gloom.ui.screen.list.OrgsListScreen
import com.materiiapps.gloom.ui.screen.list.RepositoryListScreen
import com.materiiapps.gloom.ui.screen.list.SponsoringScreen
import com.materiiapps.gloom.ui.screen.list.StarredReposListScreen
import com.materiiapps.gloom.ui.screen.profile.component.ContributionGraph
import com.materiiapps.gloom.ui.screen.profile.viewmodel.ProfileViewModel
import com.materiiapps.gloom.ui.screen.repo.component.RepoItem
import com.materiiapps.gloom.ui.screen.settings.SettingsScreen
import com.materiiapps.gloom.ui.util.EmojiUtil
import com.materiiapps.gloom.ui.util.NumberFormatter
import com.materiiapps.gloom.ui.util.navigate
import com.materiiapps.gloom.ui.widget.ReadMeCard
import com.materiiapps.gloom.ui.widget.alert.LocalAlertController
import com.materiiapps.gloom.util.Constants
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.pluralStringResource
import dev.icerock.moko.resources.compose.stringResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import java.net.URI

open class ProfileScreen(
    val user: String = ""
) : Screen {

    override val key: ScreenKey
        get() = "${this::class.simpleName}($user)"

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val viewModel: ProfileViewModel = koinScreenModel { parametersOf(user) }
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { TopBar(viewModel, scrollBehavior) }
        ) { pv ->
            PullToRefreshBox(
                isRefreshing = viewModel.isLoading,
                onRefresh = { viewModel.loadData() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pv)
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
        val shareManager: ShareManager = koinInject()

        TopAppBar(
            title = {
                val opacity = scrollBehavior.state.overlappedFraction
                val textColor by animateColorAsState(
                    targetValue = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = opacity
                    ),
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                    label = "Username fade"
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
                    IconButton(onClick = { shareManager.shareText("https://github.com/${viewModel.user!!.username}") }) {
                        Icon(Icons.Filled.Share, stringResource(Res.strings.action_share))
                    }
                }

                FollowButton(viewModel)

                if (user.isBlank()) {
                    IconButton(onClick = { nav?.navigate(SettingsScreen()) }) {
                        Icon(
                            Icons.Outlined.Settings,
                            stringResource(Res.strings.navigation_settings)
                        )
                    }
                }
            }
        )
    }

    @OptIn(ExperimentalLayoutApi::class)
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

            if (user.isFollowingYou) {
                Text(
                    text = stringResource(Res.strings.label_follows_you),
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
                if (it.isNotEmpty()) Text(
                    text = it,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
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
                            SocialAccountProvider.TWITTER -> Icons.Social.Twitter to Res.strings.cd_twitter
                            SocialAccountProvider.YOUTUBE -> Icons.Social.YouTube to Res.strings.cd_youtube
                            SocialAccountProvider.MASTODON -> Icons.Social.Mastodon to Res.strings.cd_mastodon
                            SocialAccountProvider.HOMETOWN -> Icons.Social.Hometown to Res.strings.cd_hometown
                            SocialAccountProvider.FACEBOOK -> Icons.Social.Facebook to Res.strings.cd_facebook
                            SocialAccountProvider.INSTAGRAM -> Icons.Social.Instagram to Res.strings.cd_instagram
                            SocialAccountProvider.LINKEDIN -> Icons.Social.LinkedIn to Res.strings.cd_linkedin
                            SocialAccountProvider.REDDIT -> Icons.Social.Reddit to Res.strings.cd_reddit
                            SocialAccountProvider.TWITCH -> Icons.Social.Twitch to Res.strings.cd_twitch
                            SocialAccountProvider.BLUESKY -> Icons.Social.Bluesky to Res.strings.cd_bluesky
                            SocialAccountProvider.NPM -> Icons.Social.NPM to Res.strings.cd_npm
                            else -> Icons.Outlined.Link to Res.strings.cd_link
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
                                    icon,
                                    contentDescription = stringResource(cdRes)
                                )
                            }
                        ) {
                            it.openUri(social.url)
                        }
                    }

                    user.email?.let { email ->
                        if (email.isBlank()) return@let
                        ProfileDetail(
                            text = email,
                            icon = { Icon(Icons.Outlined.Email, contentDescription = null) }
                        ) {
                            it.openUri("mailto:$email")
                        }
                    }
                }

                if (user.type == User.Type.USER) {
                    Row {
                        TextButton(onClick = {
                            if (user.username?.isNotBlank() == true) nav?.navigate(
                                FollowersScreen(
                                    user.username!!
                                )
                            )
                        }) {
                            Text(
                                pluralStringResource(
                                    Res.plurals.followers,
                                    (user.followers ?: 0).toInt(),
                                    NumberFormatter.compact((user.followers ?: 0).toInt())
                                )
                            )
                        }
                        TextButton(onClick = {
                            if (user.username?.isNotBlank() == true) nav?.navigate(
                                FollowingScreen(
                                    user.username!!
                                )
                            )
                        }) {
                            Text(
                                pluralStringResource(
                                    Res.plurals.following,
                                    (user.following ?: 0).toInt(),
                                    NumberFormatter.compact((user.following ?: 0).toInt())
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ProfileAvatar(user: ModelUser) {
        val alertController = LocalAlertController.current
        val (badge, msg) =
            if (user.isSupporter) painterResource(Res.images.img_badge_sponsor) to stringResource(
                Res.strings.badge_supporter
            )
            else if (user.id == Constants.DEV_USER_ID) painterResource(Res.images.img_badge_dev) to stringResource(
                Res.strings.badge_supporter
            )
            else null to null

        BadgedItem(
            badge =
            if (badge != null && msg != null) { ->
                Image(
                    painter = badge,
                    contentDescription = msg,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { alertController.showText(msg) }
                )
            } else null
        ) {
            Avatar(
                url = user.avatar,
                contentDescription = stringResource(
                    Res.strings.noun_users_avatar,
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
                if (status.emoji != null)
                    Image(
                        painter = rememberImagePainter(
                            EmojiUtil.emojis[status.emoji!!.replace(
                                ":",
                                ""
                            )] ?: ""
                        ),
                        contentDescription = status.emoji,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                            .size(30.dp)
                            .padding(6.dp)
                    )

                if (status.message != null)
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
                        label = stringResource(Res.strings.title_repos),
                        count = repoCount,
                        icon = Icons.Outlined.Book
                    ) {
                        username?.let { RepositoryListScreen(it) }?.let { nav?.navigate(it) }
                    }
                    if (!isOrg) {
                        StatItem(
                            label = stringResource(Res.strings.title_orgs),
                            count = orgCount,
                            icon = Icons.Outlined.Business
                        ) {
                            username?.let { OrgsListScreen(it) }?.let { nav?.navigate(it) }
                        }
                        StatItem(
                            label = stringResource(Res.strings.title_starred),
                            count = starCount,
                            icon = Icons.Outlined.Star
                        ) {
                            username?.let { StarredReposListScreen(it) }?.let { nav?.navigate(it) }
                        }
                    }
                    if (sponsoringCount > 0) {
                        StatItem(
                            label = stringResource(Res.strings.title_sponsoring),
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
                stringResource(Res.strings.section_title_pinned),
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
                Res.strings.action_unfollow_user,
                viewModel.user?.username ?: "ghost"
            )
        else Icons.Outlined.PersonAddAlt to stringResource(
            Res.strings.action_follow_user,
            viewModel.user?.username ?: "ghost"
        )

        if (viewModel.user?.canFollow == true) IconButton(onClick = { viewModel.toggleFollowing() }) {
            Icon(icon, contentDescription = alt)
        }
    }

}