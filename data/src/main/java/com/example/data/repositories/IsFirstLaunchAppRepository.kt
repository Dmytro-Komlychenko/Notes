package com.example.data.repositories

import com.example.data.storage.sharedprefs.SharedPrefsIsFirstTimeLaunch
import com.example.domain.repositoriesI.IsFirstLaunchAppRepositoryI

class IsFirstLaunchAppRepository(var sharedPrefsIsFirstTimeLaunch: SharedPrefsIsFirstTimeLaunch):
    IsFirstLaunchAppRepositoryI {
    override suspend fun launch() {
        sharedPrefsIsFirstTimeLaunch.launch()
    }

    override suspend fun get(): Boolean {
        return sharedPrefsIsFirstTimeLaunch.get()
    }
}
