package com.materiapps.gloom.domain.manager

import android.content.Context
import com.materiapps.gloom.domain.manager.base.BasePreferenceManager

class PreferenceManager(context: Context) :
    BasePreferenceManager(context.getSharedPreferences("prefs", Context.MODE_PRIVATE))