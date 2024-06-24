package com.materiiapps.gloom.ui.screens.profile

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.api.models.ModelUser
import com.materiiapps.gloom.gql.FollowingQuery
import com.materiiapps.gloom.ui.screens.list.base.BaseListScreen
import com.materiiapps.gloom.ui.viewmodels.profile.FollowingViewModel
import com.materiiapps.gloom.ui.widgets.user.UserItem
import dev.icerock.moko.resources.StringResource
import org.koin.core.parameter.parametersOf

class FollowingScreen(
    private val username: String,
) : BaseListScreen<ModelUser, FollowingQuery.Data?, FollowingViewModel>() {

    override val titleRes: StringResource get() = Res.strings.noun_following

    override val viewModel: FollowingViewModel
        @Composable get() = getScreenModel { parametersOf(username) }

    @Composable
    override fun Item(item: ModelUser) = UserItem(user = item)

    override val key: ScreenKey
        get() = "${this::class.simpleName}($username)"

}