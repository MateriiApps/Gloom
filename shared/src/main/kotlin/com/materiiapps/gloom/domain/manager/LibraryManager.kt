package com.materiiapps.gloom.domain.manager

import android.content.Context
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.util.withContext

class LibraryManager(ctx: Context) {

    val libs = Libs.Builder().withContext(ctx).build()

}