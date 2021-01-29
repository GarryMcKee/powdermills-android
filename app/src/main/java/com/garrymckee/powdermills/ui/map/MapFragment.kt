package com.garrymckee.powdermills.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions
import com.mapbox.mapboxsdk.utils.BitmapUtils
import dagger.hilt.android.AndroidEntryPoint


const val ICON_ID = "mapMarkerIconID"

@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var symbolManager: SymbolManager
    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var mapboxMap: MapboxMap
    private lateinit var style: Style
    private val activityViewModel: MainViewModel by activityViewModels()
    private val viewModel: MapViewModel by viewModels()
    private var symbol: Symbol? = null

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
                Style.Builder()
                    .fromUri(getString(R.string.mapbox_style_url))
            ) { style ->
                style.addImage(
                    ICON_ID,
                    BitmapUtils.getBitmapFromDrawable(resources.getDrawable(R.drawable.map_pin_icon))!!
                )

                val geoJsonOptions = GeoJsonOptions().withTolerance(0.4f)
                symbolManager =
                    SymbolManager(map, mapboxMap, style, null, geoJsonOptions)
                symbolManager.addClickListener { symbol ->
                    val data = symbol.data
                    requireNotNull(data)
                    viewModel.handleMapMarkerSelected(data)
                    false
                }

                symbolManager.iconAllowOverlap = true
                symbolManager.textAllowOverlap = true

                symbolManager.create(mapMarkers)

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
}