package com.example.domain.usecases

import com.example.domain.repositoriesI.IsFirstLaunchAppRepositoryI

class FirstLaunchAppUseCase(private val isFirstLaunchAppRepository: IsFirstLaunchAppRepositoryI) {
    suspend fun execute() {
        isFirstLaunchAppRepository.launch()
    }
}