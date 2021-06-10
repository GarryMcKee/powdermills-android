package com.ballincollig.powdermills.domain.map

import kotlinx.coroutines.flow.Flow

interface MapRepository {
    fun getCameraPosition(): Flow<CameraPosition>
    suspend fun saveCameraPosition(cameraPosition: CameraPosition)
    suspend fun resetCameraPosition()
}