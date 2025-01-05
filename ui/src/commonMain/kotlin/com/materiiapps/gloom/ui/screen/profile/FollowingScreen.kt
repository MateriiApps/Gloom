package com.materiiapps.gloom.ui.screen.profile

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.api.model.ModelUser
import com.materiiapps.gloom.gql.FollowingQuery
import com.materiiapps.gloom.ui.screen.list.base.BaseListScreen
import com.materiiapps.gloom.ui.screen.profile.viewmodel.FollowingViewModel
import com.materiiapps.gloom.ui.widget.user.UserItem
import dev.icerock.moko.resources.StringResource
import org.koin.core.parameter.parametersOf

class FollowingScreen(
    private val username: String,
) : BaseListScreen<ModelUser, FollowingQuery.Data?, FollowingViewModel>() {

    override val titleRes: StringResource get() = Res.strings.title_following

    override val viewModel: FollowingViewModel
        @Composable get() = koinScreenModel { parametersOf(username) }

    @Composable
    override fun Item(item: ModelUser) = UserItem(user = item)

    override val key: ScreenKey
        get() = "${this::class.simpleName}($username)"

}