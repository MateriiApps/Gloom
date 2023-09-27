package com.materiiapps.gloom.domain.manager

import android.content.Context
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.util.withContext

actual class LibraryManager(ctx: Context) {

    actual val libs = Libs.Builder().withContext(ctx).build()

}