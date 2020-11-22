package com.garrymckee.powdermills.ui.buildinglist

import com.garrymckee.powdermills.domain.building.Building

data class BuildingListUIModel(
    val title: String,
    val imageResId: Int
)

fun Building.mapToBuildingListUIModel() = BuildingListUIModel(this.name, this.menuImageResId)