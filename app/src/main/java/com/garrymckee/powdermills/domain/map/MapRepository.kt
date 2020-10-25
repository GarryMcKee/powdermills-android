package com.garrymckee.powdermills.ui.map

interface MapRepository {
    suspend fun getBuildings() : List<String>
}