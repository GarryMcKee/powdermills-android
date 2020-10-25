package com.garrymckee.powdermills.buildinglist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.garrymckee.powdermills.domain.building.BuildingRepository
import com.garrymckee.powdermills.domain.building.Building
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