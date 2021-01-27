package com.garrymckee.powdermills.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object FirebaseModule {

    @Provides
    fun provideDatabaseReference(): DatabaseReference = Firebase.database.reference

    @Provides
    fun provideFireStoreReference(): FirebaseFirestore = FirebaseFirestore.getInstance()
}