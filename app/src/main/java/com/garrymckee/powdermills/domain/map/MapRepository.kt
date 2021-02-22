package com.garrymckee.powdermills.ui.map

import com.garrymckee.powdermills.domain.map.CameraPosition

interface MapRepository {
    fun getCameraPosition(): CameraPosition
}