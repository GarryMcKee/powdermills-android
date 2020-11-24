package com.garrymckee.powdermills.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.garrymckee.powdermills.R
import com.garrymckee.powdermills.databinding.FragmentMapBinding
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.Style

class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Mapbox.getInstance(this.requireContext(), getString(R.string.mapbox_access_token))
        val binding: FragmentMapBinding = FragmentMapBinding.inflate(inflater)
        val model: MapViewModel by viewModels()
        val map = binding.mapView

        map.onCreate(savedInstanceState)
        map.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.Builder().fromUri(getString(R.string.mapbox_style_url)))
        }

        binding.showBuildingListButton.setOnClickListener {
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToBuildingListFragment())
        }

        return binding.root
    }
}