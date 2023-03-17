package com.example.data.storage.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.example.data.utils.Constants

class SharedPrefsIsFirstTimeLaunch(context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(
            Constants.SHARED_PREFS_IS_FIRST_TIME_LAUNCH,
            Context.MODE_PRIVATE
        )

    fun launch() {
        sharedPreferences.edit().putBoolean(Constants.IS_FIRST_TIME_LAUNCH, false).apply()
    }

    fun get(): Boolean {
        return sharedPreferences.getBoolean(Constants.IS_FIRST_TIME_LAUNCH, true)
    }
}