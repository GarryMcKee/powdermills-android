package com.garrymckee.powdermills.map

interface MapRepository {
    suspend fun getBuildings() : List<String>
}