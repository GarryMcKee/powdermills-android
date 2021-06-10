package com.ballincollig.powdermills

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballincollig.powdermills.ui.util.Event

class MainViewModel @ViewModelInject constructor() : ViewModel() {
    val locationPermission = MutableLiveData<Event<Boolean>>()
    val shouldCheckLocationPermissions = MutableLiveData<Event<Boolean>>(Event(false))

    fun setLocationPermission(locationPermissionGranted: Boolean) {
        locationPermission.value = Event(locationPermissionGranted)
    }

    fun checkLocationPermissions() {
        shouldCheckLocationPermissions.value = Event(true)
    }
}