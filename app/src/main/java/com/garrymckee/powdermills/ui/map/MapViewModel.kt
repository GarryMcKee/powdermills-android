package com.garrymckee.powdermills.ui.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garrymckee.powdermills.domain.building.Building
import com.garrymckee.powdermills.domain.building.BuildingRepository
import com.garrymckee.powdermills.domain.map.MapRepository
import com.garrymckee.powdermills.domain.map.mapToDomainModel
import com.garrymckee.powdermills.ui.util.Event
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MapViewModel @ViewModelInject constructor(
    private val buildingRepository: BuildingRepository,
    private val mapRepository: MapRepository
) :
    ViewModel() {

    private val buildingIdJsonField = "buildingId"
    val mapMarkersLiveData = MutableLiveData<List<SymbolOptions>>()
    val goToBuildingDetailScreenLiveData = MutableLiveData<Event<Long>>()
    val cameraPositionLiveData = MutableLiveData<Event<CameraPosition>>()

    lateinit var symbolOptionImages: List<SymbolOptionImage>

    fun loadMapData() {
        viewModelScope.launch {
            buildingRepository.observeBuildings()
                .collect { buildings ->
                    mapMarkersLiveData.value = buildings.map(::mapBuildingToMapMarker)
                    symbolOptionImages = buildings.map(::mapBuildToSymbolOptionImage)
                }
        }
    }

    fun handleMapMarkerSelected(mapMarkerData: JsonElement) {
        val buildingId = mapMarkerData.asJsonObject.get(buildingIdJsonField).asLong
        goToBuildingDetailScreenLiveData.value = Event(buildingId)
    }

    fun getCameraPosition() {
        viewModelScope.launch {
            mapRepository.getCameraPosition().collect {
                CameraPosition
                    .Builder()
                    .bearing(it.bearing)
                    .zoom(it.zoom)
                    .tilt(1.0)
                    .target(LatLng(it.latitude, it.longitude))
                    .build()
                    .let { cameraPosition ->
                        cameraPositionLiveData.value = Event(cameraPosition)
                    }
            }
        }
    }

    private fun mapBuildingToMapMarker(building: Building): SymbolOptions {
        val buildingIdJsonData = JsonObject().apply {
            addProperty(buildingIdJsonField, building.appId.toString())
        }

        return SymbolOptions()
            .withLatLng(LatLng(building.latitude, building.longitude))
            .withIconImage(building.appId.toString())
            .withIconSize(1.3f)
            .withData(buildingIdJsonData)
    }

    private fun mapBuildToSymbolOptionImage(building: Building): SymbolOptionImage =
        SymbolOptionImage(building.appId.toString(), building.iconResId)

    fun saveCameraPosition(cameraPosition: CameraPosition) {
        viewModelScope.launch {
            mapRepository.saveCameraPosition(cameraPosition.mapToDomainModel())
        }
    }

}

data class SymbolOptionImage(
    val iconId: String,
    val iconResId: Int
)