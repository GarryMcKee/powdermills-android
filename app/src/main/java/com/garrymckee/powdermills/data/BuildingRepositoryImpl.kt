package com.garrymckee.powdermills.data

import com.garrymckee.powdermills.domain.building.BuildingRepository
import com.garrymckee.powdermills.domain.building.Building
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BuildingRepositoryImpl @Inject constructor():
    BuildingRepository {
    override suspend fun observeBuildings(): Flow<List<Building>> = flow {
        delay(1000)
        emit(listOf(
            Building(
                "Building One",
                0
            ),
            Building(
                "Building Two",
                0
            ),
            Building(
                "Building Three",
                0
            )
        ))
    }
}