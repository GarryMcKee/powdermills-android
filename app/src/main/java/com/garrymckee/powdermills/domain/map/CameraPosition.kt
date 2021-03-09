package com.garrymckee.powdermills.domain.map

import com.mapbox.mapboxsdk.camera.CameraPosition

data class CameraPosition(
    val zoom: Double,
    val bearing: Double,
    val pitch: Double,
    val longitude: Double,
    val latitude: Double
)

fun CameraPosition.mapToDomainModel(): com.garrymckee.powdermills.domain.map.CameraPosition {
    return CameraPosition(
        this.zoom,
        this.bearing,
        this.tilt,
        this.target.longitude,
        this.target.latitude
    )
}