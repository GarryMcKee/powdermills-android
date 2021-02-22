package com.garrymckee.powdermills.data.map

import com.garrymckee.powdermills.domain.map.CameraPosition
import com.garrymckee.powdermills.ui.map.MapRepository
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor() : MapRepository {
    override fun getCameraPosition(): CameraPosition {
        return CameraPosition(
            13.448410483097534,
            262.6041172312613,
            8.0,
            -8.604717568652006,
            51.89136774379117
        )
    }
}