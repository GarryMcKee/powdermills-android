package com.ballincollig.powdermills.ui.buildinglist

import com.ballincollig.powdermills.domain.building.Building

data class BuildingListUIModel(
    val buildingId: Long,
    val title: String,
    val imageUrl: String
)

fun Building.mapToBuildingListUIModel() =
    BuildingListUIModel(this.appId, this.name, this.coverImageUrl)