package com.materiiapps.gloom.domain.manager

import android.content.Context
import android.os.Build
import androidx.annotation.StringRes
import com.materiiapps.gloom.R
import com.materiiapps.gloom.domain.manager.base.BasePreferenceManager

class PreferenceManager(context: Context) :
    BasePreferenceManager(context.getSharedPreferences("prefs", Context.MODE_PRIVATE)) {

    var theme by enumPreference("theme", Theme.SYSTEM)
    var monet by booleanPreference("monet", Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)

}

enum class Theme(@StringRes val label: Int) {
    SYSTEM(R.string.theme_system),
    LIGHT(R.string.theme_light),
    DARK(R.string.theme_dark);
}