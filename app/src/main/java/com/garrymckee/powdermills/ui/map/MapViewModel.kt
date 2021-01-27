package com.garrymckee.powdermills.ui.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garrymckee.powdermills.domain.building.Building
import com.garrymckee.powdermills.domain.building.BuildingRepository
import com.garrymckee.powdermills.ui.Event
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MapViewModel @ViewModelInject constructor(private val buildingRepository: BuildingRepository) :
    ViewModel() {

    private val buildingIdJsonField = "buildingId"
    val mapMarkersLiveData = MutableLiveData<List<SymbolOptions>>()
    val goToBuildingDetailScreenLiveData = MutableLiveData<Event<Long>>()

    fun loadMapData() {
        viewModelScope.launch {
            buildingRepository.observeBuildings()
                .collect { buildings ->
                    mapMarkersLiveData.value = buildings.map(::mapBuildingToMapMarker)
                }
        }
    }

    fun handleMapMarkerSelected(mapMarkerData: JsonElement) {
        val buildingId = mapMarkerData.asJsonObject.get(buildingIdJsonField).asLong
        goToBuildingDetailScreenLiveData.value = Event(buildingId)
    }

    private fun mapBuildingToMapMarker(building: Building): SymbolOptions {
        val buildingIdJsonData = JsonObject().apply {
            addProperty(buildingIdJsonField, building.appId.toString())
        }

        return SymbolOptions()
            .withLatLng(LatLng(building.latitude, building.longitude))
            .withIconImage(ICON_ID)
            .withIconSize(1.3f)
            .withData(buildingIdJsonData)
    }

}