package com.ballincollig.powdermills.data.map

import com.ballincollig.powdermills.domain.map.CameraPosition
import kotlinx.coroutines.flow.Flow

interface MapDao {
    fun observeCameraPosition(): Flow<CameraPosition>
    suspend fun saveCameraPosition(cameraPosition: CameraPosition)
    suspend fun resetCameraPosition()
}