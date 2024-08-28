package com.materiiapps.gloom.ui.screens.profile

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.api.models.ModelUser
import com.materiiapps.gloom.gql.FollowersQuery
import com.materiiapps.gloom.ui.screens.list.base.BaseListScreen
import com.materiiapps.gloom.ui.screens.profile.viewmodel.FollowersViewModel
import com.materiiapps.gloom.ui.widgets.user.UserItem
import dev.icerock.moko.resources.StringResource
import org.koin.core.parameter.parametersOf

class FollowersScreen(
    private val username: String,
) : BaseListScreen<ModelUser, FollowersQuery.Data?, FollowersViewModel>() {

    override val titleRes: StringResource get() = Res.strings.noun_followers

    override val viewModel: FollowersViewModel
        @Composable get() = getScreenModel { parametersOf(username) }

    @Composable
    override fun Item(item: ModelUser) = UserItem(user = item)

    override val key: ScreenKey
        get() = "${this::class.simpleName}($username)"

}