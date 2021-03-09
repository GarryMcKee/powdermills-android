package com.garrymckee.powdermills.data.map

import com.garrymckee.powdermills.domain.map.CameraPosition
import com.garrymckee.powdermills.domain.map.MapRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(private val mapDao: MapDao) : MapRepository {
    override fun getCameraPosition(): Flow<CameraPosition> {
        return mapDao.observeCameraPosition()
    }

    override suspend fun saveCameraPosition(cameraPosition: CameraPosition) {
        Timber.d("Saving camera position: $cameraPosition")
        mapDao.saveCameraPosition(cameraPosition)
    }

    override suspend fun resetCameraPosition() {
        mapDao.resetCameraPosition()
    }
}