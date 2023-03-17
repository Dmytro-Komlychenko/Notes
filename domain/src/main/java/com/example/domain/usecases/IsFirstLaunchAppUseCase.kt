package com.example.domain.usecases

import com.example.domain.repositoriesI.IsFirstLaunchAppRepositoryI

class IsFirstLaunchAppUseCase(private val isFirstLaunchAppRepository: IsFirstLaunchAppRepositoryI) {
    suspend fun execute(): Boolean {
        return isFirstLaunchAppRepository.get()
    }
}