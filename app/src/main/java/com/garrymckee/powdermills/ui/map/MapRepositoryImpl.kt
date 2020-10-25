package com.garrymckee.powdermills.ui.map

class MapRepositoryImpl : MapRepository {
    override suspend fun getBuildings(): List<String> {
        return listOf("A","B","C")
    }

}