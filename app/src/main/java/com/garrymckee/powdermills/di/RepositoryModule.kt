package com.garrymckee.powdermills.di

import com.garrymckee.powdermills.data.buildinglist.RemoteBuildingRepositoryImpl
import com.garrymckee.powdermills.data.useragreement.UserAgreementRepositoryImpl
import com.garrymckee.powdermills.domain.building.BuildingRepository
import com.garrymckee.powdermills.domain.useragreement.UserAgreementRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindBuildingRepository(
        remoteBuildingRepositoryImpl: RemoteBuildingRepositoryImpl
    ): BuildingRepository

    @Binds
    abstract fun bindUserAgreementRepository(
        userAgreementRepositoryImpl: UserAgreementRepositoryImpl
    ): UserAgreementRepository
}