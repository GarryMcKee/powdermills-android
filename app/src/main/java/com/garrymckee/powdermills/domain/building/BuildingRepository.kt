package com.garrymckee.powdermills.domain.building

import com.garrymckee.powdermills.domain.building.Building
import kotlinx.coroutines.flow.Flow

interface BuildingRepository {
    suspend fun observeBuildings(): Flow<List<Building>>
}