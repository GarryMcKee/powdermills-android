package com.garrymckee.powdermills.map

class MapRepositoryImpl : MapRepository {
    override suspend fun getBuildings(): List<String> {
        return listOf("A","B","C")
    }

}