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

    val carouselImageResIds = MutableLiveData<List<String>>()
    val titleTextLiveData = MutableLiveData<String>()
    val historyTextLiveData = MutableLiveData<String>()
    val functionTextLiveData = MutableLiveData<String>()
    val triviaTextLiveData = MutableLiveData<String>()
    val showFunFactsLiveData = MutableLiveData<Boolean>()
    val hideFunFactsLiveData = MutableLiveData<Boolean>()

    fun observeBuildingWithId(buildingId: Long) =
        viewModelScope.launch {
            buildingRepository.observeBuildingWithId(buildingId)
                .collect { building ->
                    renderBuildingDetail(building)
                }
        }

    private fun renderBuildingDetail(buildingDetail: Building) {
        carouselImageResIds.value = buildingDetail.otherImageUrls
        titleTextLiveData.value = buildingDetail.name
        historyTextLiveData.value = buildingDetail.history
        functionTextLiveData.value = buildingDetail.function
        renderFunFacts(buildingDetail.funFacts)
    }

    private fun renderFunFacts(funfacts: List<String>) {
        if (funfacts.isNotEmpty()) {
            showFunFactsLiveData.value = true
            triviaTextLiveData.value = funfacts.joinToString(separator = "\n")
        } else {
            hideFunFactsLiveData.value = true
        }
    }
}