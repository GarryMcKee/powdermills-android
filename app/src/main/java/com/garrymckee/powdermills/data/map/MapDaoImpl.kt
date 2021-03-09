package com.garrymckee.powdermills.data.map

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.garrymckee.powdermills.domain.map.CameraPosition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val ZOOM_KEY = doublePreferencesKey("zoom_key")
val BEARING_KEY = doublePreferencesKey("bearing_key")
val PITCH_KEY = doublePreferencesKey("pitch_key")
val LONGITUDE_KEY = doublePreferencesKey("longitude_key")
val LATITUDE_KEY = doublePreferencesKey("latitude_key")

const val DEFAULT_ZOOM = 13.448410483097534
const val DEFAULT_BEARING = 262.6041172312613
const val DEFAULT_PITCH = 8.0
const val DEFAULT_LONGITUDE = -8.604717568652006
const val DEFAULT_LATITUDE = 51.89136774379117

class MapDaoImpl @Inject constructor(private val dataStore: DataStore<Preferences>) : MapDao {
    override fun observeCameraPosition(): Flow<CameraPosition> {
        return dataStore.data
            .map {
                CameraPosition(
                    it[ZOOM_KEY] ?: DEFAULT_ZOOM,
                    it[BEARING_KEY] ?: DEFAULT_BEARING,
                    it[PITCH_KEY] ?: DEFAULT_PITCH,
                    it[LONGITUDE_KEY] ?: DEFAULT_LONGITUDE,
                    it[LATITUDE_KEY] ?: DEFAULT_LATITUDE
                )
            }
    }

    override suspend fun saveCameraPosition(cameraPosition: CameraPosition) {
        dataStore.edit {
            it[ZOOM_KEY] = cameraPosition.zoom
            it[BEARING_KEY] = cameraPosition.bearing
            it[PITCH_KEY] = cameraPosition.pitch
            it[LONGITUDE_KEY] = cameraPosition.longitude
            it[LATITUDE_KEY] = cameraPosition.latitude
        }
    }

    override suspend fun resetCameraPosition() {
        dataStore.edit {
            it[ZOOM_KEY] = DEFAULT_ZOOM
            it[BEARING_KEY] = DEFAULT_BEARING
            it[PITCH_KEY] = DEFAULT_PITCH
            it[LONGITUDE_KEY] = DEFAULT_LONGITUDE
            it[LATITUDE_KEY] = DEFAULT_LATITUDE
        }
    }
}