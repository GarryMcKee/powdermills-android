package com.ballincollig.powdermills.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
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
import com.ballincollig.powdermills.MainViewModel
import com.ballincollig.powdermills.R
import com.ballincollig.powdermills.databinding.FragmentMapBinding
import com.ballincollig.powdermills.ui.util.setOnSingleClickListener
import com.google.android.material.snackbar.Snackbar
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
import timber.log.Timber

@AndroidEntryPoint
class MapFragment : Fragment(), OnSymbolClickListener {

    private lateinit var symbolManager: SymbolManager
    private lateinit var binding: FragmentMapBinding
    private lateinit var style: Style
    private val activityViewModel: MainViewModel by activityViewModels()
    private val viewModel: MapViewModel by viewModels()

    /*
    Mapbox requires a call to mapView.onSaveInstanceState to tie in with app lifecycle methods
    However, onSaveInstanceState can in fact be called before onCreateView meaning intermittently
    we can end up with uninitialised propety except when using late init for map view.

    Work around for this is set a nullable map view and appropriate safe calls
     */
    private var mapboxMap: MapboxMap? = null
    private var mapView: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Mapbox.getInstance(this.requireContext(), getString(R.string.mapbox_access_token))
        binding = FragmentMapBinding.inflate(inflater)
        mapView = binding.mapView
        mapView?.onCreate(savedInstanceState)

        setupBindings()
        setupSubscriptions()

        viewModel.loadMapData()

        return binding.root
    }

    private fun setupSubscriptions() {
        viewModel.goToBuildingDetailScreenLiveData.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { buildingId ->
                findNavController().navigate(
                    MapFragmentDirections.actionMapFragmentToBuildingDetailFragment(
                        buildingId
                    )
                )
            }
        })

        viewModel.mapMarkersLiveData.observe(viewLifecycleOwner, Observer { mapMarkers ->
            mapView?.let {
                setupMap(it, mapMarkers)
            }
        })
    }

    private fun onMapLoadedSubscriptions() {
        viewModel.cameraPositionLiveData.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { cameraPosition ->
                mapboxMap?.cameraPosition = cameraPosition
                Timber.d(cameraPosition.toString())
            }
        })
    }

    private fun setupBindings() {
        binding.showBuildingListButton.setOnSingleClickListener {
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToBuildingListFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onPause() {
        super.onPause()
        mapboxMap?.let {
            viewModel.saveCameraPosition(it.cameraPosition)
        }
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
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
        mapView?.onSaveInstanceState(outState)
    }

    private fun setupMap(
        map: MapView,
        mapMarkers: List<SymbolOptions>
    ) {
        map.getMapAsync { mapboxMap ->

            mapboxMap.setStyle(
                Style.Builder().fromUri(getString(R.string.mapbox_style_url))
            ) { style ->

                viewModel.getCameraPosition()
                setUpMapPins(map, mapboxMap, style, mapMarkers)
                setupAttributionFeatures(mapboxMap)

                this.style = style
                this.mapboxMap = mapboxMap
                checkLocationPermissions()
                onMapLoadedSubscriptions()
            }
        }
    }

    private fun setupAttributionFeatures(mapboxMap: MapboxMap) {
        mapboxMap.uiSettings.isAttributionEnabled = true
        mapboxMap.uiSettings.isCompassEnabled = false
        mapboxMap.uiSettings.isLogoEnabled = true
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

    @SuppressLint("MissingPermission")
    private fun setUpUserLocation() {
        try {
            mapboxMap?.let {
                val locationComponentOptions = LocationComponentOptions.builder(requireContext())
                    .foregroundDrawable(R.drawable.ic_you_are_here)
                    .pulseEnabled(true)
                    .pulseColor(Color.WHITE)
                    .pulseAlpha(.4f)
                    .bearingTintColor(R.color.vpi__bright_foreground_inverse_holo_light)
                    .build()

                val locationComponentActivationOptions = LocationComponentActivationOptions
                    .builder(requireContext(), style)
                    .locationComponentOptions(locationComponentOptions)
                    .build()

                val locationComponent = it.locationComponent
                locationComponent.activateLocationComponent(locationComponentActivationOptions)
                locationComponent.isLocationComponentEnabled = true

                locationComponent.setCameraMode(CameraMode.NONE, 0, 15.0, null, null, null)
                locationComponent.renderMode = RenderMode.COMPASS
            }

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