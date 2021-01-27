package com.garrymckee.powdermills.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.garrymckee.powdermills.R
import com.garrymckee.powdermills.databinding.FragmentMapBinding
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions
import com.mapbox.mapboxsdk.utils.BitmapUtils
import dagger.hilt.android.AndroidEntryPoint


const val ICON_ID = "mapMarkerIconID"
const val LAYER_ID = "mapLayerID"
const val SOURCE_ID = "mapSourceID"

@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var symbolManager: SymbolManager
    private lateinit var map: MapView
    private val viewModel: MapViewModel by viewModels()
    private var symbol: Symbol? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Mapbox.getInstance(this.requireContext(), getString(R.string.mapbox_access_token))
        val binding: FragmentMapBinding = FragmentMapBinding.inflate(inflater)
        map = binding.mapView
        map.onCreate(savedInstanceState)

        viewModel.mapMarkersLiveData.observe(viewLifecycleOwner, Observer { mapMarkers ->
            setupMap(map, savedInstanceState, mapMarkers)
        })

        viewModel.goToBuildingDetailScreenLiveData.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { buildingId ->
                findNavController().navigate(
                    MapFragmentDirections.actionMapFragmentToBuildingDetailFragment(
                        buildingId
                    )
                )
            }
        })

        viewModel.loadMapData()

        binding.showBuildingListButton.setOnClickListener {
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToBuildingListFragment())
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map.onSaveInstanceState(outState)
    }


    private fun setupMap(
        map: MapView,
        savedInstanceState: Bundle?,
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
            }
        }
    }
}