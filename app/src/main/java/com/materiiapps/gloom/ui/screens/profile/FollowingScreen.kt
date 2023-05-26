package com.materiiapps.gloom.ui.screens.profile

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.materiiapps.gloom.gql.FollowingQuery
import com.materiiapps.gloom.R
import com.materiiapps.gloom.domain.models.ModelUser
import com.materiiapps.gloom.ui.screens.list.base.BaseListScreen
import com.materiiapps.gloom.ui.viewmodels.profile.FollowingViewModel
import com.materiiapps.gloom.ui.widgets.user.UserItem
import org.koin.core.parameter.parametersOf

class FollowingScreen(
    private val username: String,
    override val titleRes: Int = R.string.noun_following
) : BaseListScreen<ModelUser, FollowingQuery.Data?, FollowingViewModel>() {

    override val viewModel: FollowingViewModel
        @Composable get() = getScreenModel { parametersOf(username) }

    @Composable
    override fun Item(item: ModelUser) = UserItem(user = item)

    override val key: ScreenKey
        get() = "${javaClass.name}($username)"

}