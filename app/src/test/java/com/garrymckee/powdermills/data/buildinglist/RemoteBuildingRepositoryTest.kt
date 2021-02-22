package com.garrymckee.powdermills.data.buildinglist

import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Before

class RemoteBuildingRepositoryTest {

    lateinit var mockFireStore: FirebaseFirestore

    val repository = RemoteBuildingRepository(mockFireStore)

    @Before
    fun setup() {

    }
}