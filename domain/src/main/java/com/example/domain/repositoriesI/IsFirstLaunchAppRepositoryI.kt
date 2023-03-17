package com.example.domain.repositoriesI

interface IsFirstLaunchAppRepositoryI {
    suspend fun launch()
    suspend fun get(): Boolean
}