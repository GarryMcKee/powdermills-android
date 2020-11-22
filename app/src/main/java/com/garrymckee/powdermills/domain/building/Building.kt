package com.garrymckee.powdermills.domain.building

data class Building(
    val name: String,
    val menuImageResId: Int,
    val carouselImageResIds: List<Int>,
    val history: String,
    val function: String,
    val trivia: String
)
