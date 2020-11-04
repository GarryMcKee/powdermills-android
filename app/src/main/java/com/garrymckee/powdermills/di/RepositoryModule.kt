package com.garrymckee.powdermills.di

import com.garrymckee.powdermills.data.buildinglist.BuildingRepositoryImpl
import com.garrymckee.powdermills.data.useragreement.UserAgreementRepositoryImpl
import com.garrymckee.powdermills.domain.building.BuildingRepository
import com.garrymckee.powdermills.domain.useragreement.UserAgreementRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

class RepositoryModule {
    @Module
    @InstallIn(ActivityComponent::class)
    abstract class AnalyticsModule {

        @Binds
        abstract fun bindBuildingRepository(
            buildingRepositoryImpl: BuildingRepositoryImpl
        ): BuildingRepository

        @Binds
        abstract fun bindUserAgreementRepositor(
            userAgreementRepositoryImpl: UserAgreementRepositoryImpl
        ): UserAgreementRepository
    }
}