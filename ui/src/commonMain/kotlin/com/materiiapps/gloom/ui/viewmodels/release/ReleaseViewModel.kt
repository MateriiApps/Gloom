package com.materiiapps.gloom.ui.viewmodels.release

import com.materiiapps.gloom.gql.ReleaseDetailsQuery
import com.materiiapps.gloom.gql.fragment.ReleaseAssetFragment
import com.materiiapps.gloom.ui.viewmodels.list.base.BaseListViewModel

expect class ReleaseViewModel : BaseListViewModel<ReleaseAssetFragment, ReleaseDetailsQuery.Data?>