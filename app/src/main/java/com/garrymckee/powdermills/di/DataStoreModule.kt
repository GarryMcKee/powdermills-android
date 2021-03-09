package com.garrymckee.powdermills.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.garrymckee.powdermills.data.map.MapDao
import com.garrymckee.powdermills.data.map.MapDaoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "powdermills-datastore")

@Module
@InstallIn(ActivityComponent::class)
object DataStoreModule {

    @Provides
    fun provideMapDao(@ApplicationContext context: Context): MapDao = MapDaoImpl(context.dataStore)
}