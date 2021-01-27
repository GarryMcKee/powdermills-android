package com.garrymckee.powdermills.data.buildinglist

import android.util.Log
import com.garrymckee.powdermills.domain.building.Building
import com.garrymckee.powdermills.domain.building.BuildingRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


open class RemoteBuildingRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    BuildingRepository {

    @ExperimentalCoroutinesApi
    override suspend fun observeBuildings(): Flow<List<Building>> = callbackFlow {
        firestore.collection(BUILDINGS_COLLECTION_KEY).get()
            .addOnSuccessListener { result ->
                val buildingsResponse = result.toObjects(BuildingResponse::class.java)
                    .map(BuildingResponse::mapToDomainModel)

                offer(buildingsResponse)
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
                val building = results.toObjects(BuildingResponse::class.java)
                    .map(BuildingResponse::mapToDomainModel)
                    .first()

                offer(building)
            }

        awaitClose {
            Log.d("GET BUILDINGS", "GET BUILDINGS CLOSED")
        }
    }

}

fun BuildingResponse.mapToDomainModel() = Building(
    appId,
    buildingName,
    coverImageUrl,
    otherImages,
    history,
    function,
    funFacts,
    latitude,
    longitude
)
