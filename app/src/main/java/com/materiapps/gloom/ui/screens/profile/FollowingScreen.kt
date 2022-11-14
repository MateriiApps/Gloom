package com.materiapps.gloom.ui.screens.profile

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.materiapps.gloom.FollowingQuery
import com.materiapps.gloom.R
import com.materiapps.gloom.domain.models.ModelUser
import com.materiapps.gloom.ui.screens.list.base.BaseListScreen
import com.materiapps.gloom.ui.viewmodels.profile.FollowingViewModel
import com.materiapps.gloom.ui.widgets.user.UserItem
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