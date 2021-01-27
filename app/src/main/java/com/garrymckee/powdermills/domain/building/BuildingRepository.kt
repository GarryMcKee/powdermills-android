package com.garrymckee.powdermills.domain.building

import kotlinx.coroutines.flow.Flow

interface BuildingRepository {
    suspend fun observeBuildings(): Flow<List<Building>>
    suspend fun observeBuildingWithId(id: Long): Flow<Building>
}