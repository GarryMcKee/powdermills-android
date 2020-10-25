package com.garrymckee.powdermills.ui.buildinglist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garrymckee.powdermills.domain.building.Building
import com.garrymckee.powdermills.domain.building.BuildingRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BuildingListViewModel @ViewModelInject constructor(
    private val buildingRepository: BuildingRepository
) : ViewModel() {
    val buildingList = MutableLiveData<List<Building>>()

    fun observeBuildings() =
        viewModelScope.launch {
            buildingRepository.observeBuildings()
                .collect { buildings ->
                    buildingList.postValue(buildings)
                }
        }
}