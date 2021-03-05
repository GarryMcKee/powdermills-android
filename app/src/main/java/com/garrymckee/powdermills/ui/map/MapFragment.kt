package com.garrymckee.powdermills.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.garrymckee.powdermills.MainViewModel
import com.garrymckee.powdermills.R
import com.garrymckee.powdermills.databinding.FragmentMapBinding
import com.garrymckee.powdermills.ui.util.setOnSingleClickListener
import com.google.android.material.snackbar.Snackbar
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions
import dagger.hilt.android.AndroidEntryPoint


const val ICON_ID = "mapMarkerIconID"

@AndroidEntryPoint
class MapFragment : Fragment(), OnSymbolClickListener {

    private lateinit var symbolManager: SymbolManager
    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var mapboxMap: MapboxMap
    private lateinit var style: Style
    private val activityViewModel: MainViewModel by activityViewModels()
    private val viewModel: MapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Mapbox.getInstance(this.requireContext(), getString(R.string.mapbox_access_token))
        binding = FragmentMapBinding.inflate(inflater)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)

        viewModel.goToBuildingDetailScreenLiveData.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { buildingId ->
                findNavController().navigate(
                    MapFragmentDirections.actionMapFragmentToBuildingDetailFragment(
                        buildingId
                    )
                )
            }
        })

        binding.showBuildingListButton.setOnSingleClickListener {
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToBuildingListFragment())
        }

        viewModel.mapMarkersLiveData.observe(viewLifecycleOwner, Observer { mapMarkers ->
            setupMap(mapView, mapMarkers)
        })

        viewModel.loadMapData()

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun checkLocationPermissions() {
        activityViewModel.locationPermission.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { locationPermissionGranted ->
                if (locationPermissionGranted) {
                    setUpUserLocation()
                }
            }
        })

        activityViewModel.checkLocationPermissions()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    private fun setupMap(
        map: MapView,
        mapMarkers: List<SymbolOptions>
    ) {

        map.getMapAsync { mapboxMap ->

            mapboxMap.setStyle(
                Style.Builder().fromUri(getString(R.string.mapbox_style_url))
            ) { style ->

                setCameraPosition(mapboxMap)
                setUpMapPins(map, mapboxMap, style, mapMarkers)
                setupAttributionFeatures(mapboxMap)

                this.style = style
                this.mapboxMap = mapboxMap
                checkLocationPermissions()
            }
        }
    }

    private fun setupAttributionFeatures(mapboxMap: MapboxMap) {
        mapboxMap.uiSettings.isAttributionEnabled = false
        mapboxMap.uiSettings.isCompassEnabled = false
        mapboxMap.uiSettings.isLogoEnabled = false
    }

    private fun setUpMapPins(
        map: MapView,
        mapboxMap: MapboxMap,
        style: Style,
        mapMarkers: List<SymbolOptions>
    ) {
        viewModel.symbolOptionImages.forEach {
            style.addImage(
                it.iconId,
                getBitmapFromVectorDrawable(requireContext(), it.iconResId)!!
            )
        }

        val geoJsonOptions = GeoJsonOptions().withTolerance(0.4f)
        symbolManager =
            SymbolManager(map, mapboxMap, style, null, geoJsonOptions)

        symbolManager.addClickListener(this)

        symbolManager.iconAllowOverlap = true
        symbolManager.textAllowOverlap = true
        symbolManager.create(mapMarkers)
    }

    private fun setCameraPosition(mapboxMap: MapboxMap) {
        viewModel.getCameraPosition()
            .run {
                CameraPosition
                    .Builder()
                    .bearing(bearing)
                    .zoom(zoom)
                    .tilt(1.0)
                    .target(LatLng(latitude, longitude))
                    .build()
            }.let {
                mapboxMap.cameraPosition = it
            }
    }

    @SuppressLint("MissingPermission")
    private fun setUpUserLocation() {
        try {
            val locationComponentOptions = LocationComponentOptions.builder(requireContext())
                .foregroundDrawable(R.drawable.mapbox_marker_icon_default)
                .bearingTintColor(R.color.vpi__bright_foreground_inverse_holo_light)
                .build()

            val locationComponentActivationOptions = LocationComponentActivationOptions
                .builder(requireContext(), style)
                .locationComponentOptions(locationComponentOptions)
                .build()

            val locationComponent = mapboxMap.locationComponent
            locationComponent.activateLocationComponent(locationComponentActivationOptions)
            locationComponent.isLocationComponentEnabled = true

            locationComponent.setCameraMode(CameraMode.NONE, 0, 15.0, null, null, null)
            locationComponent.renderMode = RenderMode.COMPASS
        } catch (e: Exception) {
            Snackbar.make(
                requireView(),
                "Could not get your current location, please make sure location is turned on for your device",
                Snackbar.LENGTH_SHORT
            )
        }

    }
    override fun onAnnotationClick(symbol: Symbol?): Boolean {
        val data = symbol?.data
        requireNotNull(data)
        viewModel.handleMapMarkerSelected(data)
        return true
    }

    private fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap? {
        val drawable: Drawable? = ContextCompat.getDrawable(context, drawableId)
        val bitmap: Bitmap = Bitmap.createBitmap(
            drawable?.intrinsicWidth!!,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}