package com.ballincollig.powdermills.data.buildinglist

import android.util.Log
import com.ballincollig.powdermills.R
import com.ballincollig.powdermills.data.offerSafe
import com.ballincollig.powdermills.domain.building.Building
import com.ballincollig.powdermills.domain.building.BuildingRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class RemoteBuildingRepository @Inject constructor(private val firestore: FirebaseFirestore) :
    BuildingRepository {

    @ExperimentalCoroutinesApi
    override suspend fun observeBuildings(): Flow<List<Building>> = callbackFlow {
        firestore.collection(BUILDINGS_COLLECTION_KEY).get()
            .addOnSuccessListener { result ->
                val buildingsResponse = result.toObjects(BuildingResponse::class.java)
                    .map(BuildingResponse::mapToDomainModel)
                offerSafe(buildingsResponse)
            }

        awaitClose {
            Log.d("GET BUILDINGS", "GET BUILDINGS CLOSED")
        }
    }


    @ExperimentalCoroutinesApi
    override suspend fun observeBuildingWithId(id: Long): Flow<Building> = callbackFlow {
        firestore.collection(BUILDINGS_COLLECTION_KEY)
            .whereEqualTo(BUILDING_ID_ITEM_KEY, id)
            .get()
            .addOnSuccessListener { results ->
                results.toObjects(BuildingResponse::class.java)
                    .map(BuildingResponse::mapToDomainModel)
                    .first()
                    .let { offerSafe(it) }
            }

        awaitClose {
            Log.d("GET BUILDINGS", "GET BUILDINGS CLOSED")
        }
    }

}

fun BuildingResponse.mapToDomainModel(): Building {

    return Building(
        appId,
        buildingName,
        coverImageUrl,
        otherImages,
        history,
        function,
        funFacts,
        latitude,
        longitude,
        getIconForBuilding(appId)
    )
}

fun getIconForBuilding(id: Long): Int =
    when (id) {
        1L -> R.drawable.ic_building_01_symbol
        2L -> R.drawable.ic_building_02_symbol
        3L -> R.drawable.ic_building_03_symbol
        4L -> R.drawable.ic_building_04_symbol
        5L -> R.drawable.ic_building_05_symbol
        6L -> R.drawable.ic_building_06_symbol
        7L -> R.drawable.ic_building_07_symbol
        8L -> R.drawable.ic_building_08_symbol
        9L -> R.drawable.ic_building_09_symbol
        10L -> R.drawable.ic_building_10_symbol
        11L -> R.drawable.ic_building_11_symbol
        12L -> R.drawable.ic_building_12_symbol
        13L -> R.drawable.ic_building_14_symbol
        14L -> R.drawable.ic_building_15_symbol
        15L -> R.drawable.ic_building_16_symbol

        else -> {
            throw IllegalArgumentException("Unsupported building ID: $id")
        }
    }
