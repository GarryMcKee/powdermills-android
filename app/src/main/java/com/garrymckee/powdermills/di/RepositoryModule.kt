package com.garrymckee.powdermills.di

import com.garrymckee.powdermills.data.buildinglist.RemoteBuildingRepository
import com.garrymckee.powdermills.data.map.MapRepositoryImpl
import com.garrymckee.powdermills.data.useragreement.UserAgreementRepositoryImpl
import com.garrymckee.powdermills.domain.building.BuildingRepository
import com.garrymckee.powdermills.domain.useragreement.UserAgreementRepository
import com.garrymckee.powdermills.ui.map.MapRepository
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