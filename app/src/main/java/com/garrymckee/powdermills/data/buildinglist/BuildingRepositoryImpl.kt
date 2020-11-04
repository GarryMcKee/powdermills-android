package com.garrymckee.powdermills.data.buildinglist

import com.garrymckee.powdermills.R
import com.garrymckee.powdermills.domain.building.Building
import com.garrymckee.powdermills.domain.building.BuildingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BuildingRepositoryImpl @Inject constructor() :
    BuildingRepository {
    override suspend fun observeBuildings(): Flow<List<Building>> = flow {
        emit(
            listOf(
                Building(
                    "Powder Mill",
                    R.drawable.building3
                ),
                Building(
                    "Incorporating Mill",
                    R.drawable.building4
                ),
                Building(
                    "Dusthouse",
                    R.drawable.building2
                ),
                Building(
                    "Sawmill",
                    R.drawable.building3
                ),
                Building(
                    "Cooperage",
                    R.drawable.building4
                ),
                Building(
                    "Drying House",
                    R.drawable.building2
                ),
                Building(
                    "Press House",
                    R.drawable.building2
                )
            )
        )
    }
}