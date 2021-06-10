package com.ballincollig.powdermills.domain.building

data class Building(
    val appId: Long,
    val name: String,
    val coverImageUrl: String,
    val otherImageUrls: List<String>,
    val history: String,
    val function: String,
    val funFacts: List<String>,
    val latitude: Double,
    val longitude: Double,
    val iconResId: Int
)
