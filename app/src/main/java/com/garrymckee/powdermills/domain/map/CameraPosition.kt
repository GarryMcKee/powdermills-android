package com.garrymckee.powdermills.domain.map

data class CameraPosition(
    val zoom: Double,
    val bearing: Double,
    val pitch: Double,
    val longitude: Double,
    val latitude: Double
)