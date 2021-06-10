package com.ballincollig.powdermills.di

import com.ballincollig.powdermills.data.buildinglist.RemoteBuildingRepository
import com.ballincollig.powdermills.data.map.MapRepositoryImpl
import com.ballincollig.powdermills.data.useragreement.UserAgreementRepositoryImpl
import com.ballincollig.powdermills.domain.building.BuildingRepository
import com.ballincollig.powdermills.domain.map.MapRepository
import com.ballincollig.powdermills.domain.useragreement.UserAgreementRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindBuildingRepository(
        remoteBuildingRepository: RemoteBuildingRepository
    ): BuildingRepository

    @Binds
    abstract fun bindUserAgreementRepository(
        userAgreementRepositoryImpl: UserAgreementRepositoryImpl
    ): UserAgreementRepository

    @Binds
    abstract fun bindMapRepository(
        mapRepositoryImpl: MapRepositoryImpl
    ): MapRepository
}