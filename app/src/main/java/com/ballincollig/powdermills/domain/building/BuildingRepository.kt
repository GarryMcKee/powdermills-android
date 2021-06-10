package com.ballincollig.powdermills.domain.building

import kotlinx.coroutines.flow.Flow

interface BuildingRepository {
    suspend fun observeBuildings(): Flow<List<Building>>
    suspend fun observeBuildingWithId(id: Long): Flow<Building>
}