package com.garrymckee.powdermills.ui.buildingdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garrymckee.powdermills.domain.building.Building
import com.garrymckee.powdermills.domain.building.BuildingRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BuildingDetailViewModel @ViewModelInject constructor(
    private val buildingRepository: BuildingRepository
) : ViewModel() {

    val carouselImageResIds = MutableLiveData<List<Int>>()
    val titleTextLiveData = MutableLiveData<String>()
    val historyTextLiveData = MutableLiveData<String>()
    val functionTextLiveData = MutableLiveData<String>()
    val triviaTextLiveData = MutableLiveData<String>()

    fun observeBuildingWithId(buildingId: String) =
        viewModelScope.launch {
            buildingRepository.observeBuildingWithId(buildingId)
                .collect { building ->
                    renderBuildingDetail(building)
                }
        }

    private fun renderBuildingDetail(buildingDetail: Building) {
        carouselImageResIds.value = buildingDetail.carouselImageResIds
        titleTextLiveData.value = buildingDetail.name
        historyTextLiveData.value = buildingDetail.history
        functionTextLiveData.value = buildingDetail.function
        triviaTextLiveData.value = buildingDetail.trivia
    }
}