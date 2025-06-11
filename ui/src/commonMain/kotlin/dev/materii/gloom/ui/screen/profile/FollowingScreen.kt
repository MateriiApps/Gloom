package dev.materii.gloom.ui.screen.profile

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import dev.icerock.moko.resources.StringResource
import dev.materii.gloom.Res
import dev.materii.gloom.api.model.ModelUser
import dev.materii.gloom.gql.FollowingQuery
import dev.materii.gloom.ui.screen.list.base.BaseListScreen
import dev.materii.gloom.ui.screen.profile.viewmodel.FollowingViewModel
import dev.materii.gloom.ui.widget.user.UserItem
import org.koin.core.parameter.parametersOf

class FollowingScreen(
    private val username: String,
): BaseListScreen<ModelUser, FollowingQuery.Data?, FollowingViewModel>() {

    override val titleRes: StringResource get() = Res.strings.title_following

    override val viewModel: FollowingViewModel
        @Composable get() = koinScreenModel { parametersOf(username) }

    @Composable
    override fun Item(item: ModelUser) = UserItem(user = item)

    override val key: ScreenKey
        get() = "${this::class.simpleName}($username)"

}