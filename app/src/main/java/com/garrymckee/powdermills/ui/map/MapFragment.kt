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
import com.mapbox.mapboxsdk.Mapbox
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

        binding.showBuildingListButton.setOnClickListener {
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
        symbolManager.removeClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    private fun checkLocationPermissions() {
        activityViewModel.locationPermission.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { locationPermissionGranted ->
                if (locationPermissionGranted) {
                    centreMapOnUser()
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
                mapboxMap.uiSettings.isAttributionEnabled = false
                mapboxMap.uiSettings.isCompassEnabled = false
                mapboxMap.uiSettings.isLogoEnabled = false
                this.style = style
                this.mapboxMap = mapboxMap
                checkLocationPermissions()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun centreMapOnUser() {
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

        locationComponent.setCameraMode(CameraMode.TRACKING, 0, 15.0, null, null, null)
        locationComponent.renderMode = RenderMode.COMPASS
    }

    override fun onAnnotationClick(symbol: Symbol?): Boolean {
        val data = symbol?.data
        requireNotNull(data)
        viewModel.handleMapMarkerSelected(data)
        return false
    }

    private fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap? {
        var drawable: Drawable? = ContextCompat.getDrawable(context, drawableId)
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