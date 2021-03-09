package com.garrymckee.powdermills.data.map

import com.garrymckee.powdermills.domain.map.CameraPosition
import kotlinx.coroutines.flow.Flow

interface MapDao {
    fun observeCameraPosition(): Flow<CameraPosition>
    suspend fun saveCameraPosition(cameraPosition: CameraPosition)
    suspend fun resetCameraPosition()
}