package com.garrymckee.powdermills.domain.building

data class Building(
    val appId: String,
    val name: String,
    val coverImageUrl: String,
    val otherImageUrls: List<String>,
    val history: String,
    val function: String,
    val trivia: String
)
