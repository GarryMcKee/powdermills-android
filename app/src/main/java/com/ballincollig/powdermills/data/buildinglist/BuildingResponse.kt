package com.ballincollig.powdermills.data.buildinglist

import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName

const val BUILDINGS_COLLECTION_KEY = "Buildings"
const val FUN_FACTS_ITEM_KEY = "funFacts"
const val FUNCTION_ITEM_KEY = "function"
const val HISTORY_ITEM_KEY = "history"
const val LATITUDE_ITEM_KEY = "latitude"
const val LONGITUDE_ITEM_KEY = "longitude"
const val BUILDING_NAME_ITEM_KEY = "name"
const val BUILDING_ID_ITEM_KEY = "orders"
const val COVER_IMAGE_PATH_ITEM_KEY = "coverImagePath"
const val IMAGE_PATHS_KEY = "imagePaths"

@IgnoreExtraProperties
data class BuildingResponse(
    @get:PropertyName(BUILDING_ID_ITEM_KEY) @set:PropertyName(BUILDING_ID_ITEM_KEY)
    var appId: Long = 0,
    @get:PropertyName(BUILDING_NAME_ITEM_KEY) @set:PropertyName(BUILDING_NAME_ITEM_KEY)
    var buildingName: String = "",
    @get:PropertyName(COVER_IMAGE_PATH_ITEM_KEY) @set:PropertyName(COVER_IMAGE_PATH_ITEM_KEY)
    var coverImageUrl: String = "",
    @get:PropertyName(FUN_FACTS_ITEM_KEY) @set:PropertyName(FUN_FACTS_ITEM_KEY)
    var funFacts: List<String> = emptyList(),
    @get:PropertyName(FUNCTION_ITEM_KEY) @set:PropertyName(FUNCTION_ITEM_KEY)
    var function: String = "",
    @get:PropertyName(LATITUDE_ITEM_KEY) @set:PropertyName(LATITUDE_ITEM_KEY)
    var latitude: Double = 0.0,
    @get:PropertyName(LONGITUDE_ITEM_KEY) @set:PropertyName(LONGITUDE_ITEM_KEY)
    var longitude: Double = 0.0,
    @get:PropertyName(IMAGE_PATHS_KEY) @set:PropertyName(IMAGE_PATHS_KEY)
    var otherImages: List<String> = emptyList(),
    @get:PropertyName(HISTORY_ITEM_KEY) @set:PropertyName(HISTORY_ITEM_KEY)
    var history: String = ""
)