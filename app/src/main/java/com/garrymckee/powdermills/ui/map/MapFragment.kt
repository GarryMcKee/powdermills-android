package com.garrymckee.powdermills.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.garrymckee.powdermills.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentMapBinding = FragmentMapBinding.inflate(inflater)

        val model: MapViewModel by viewModels()

        binding.showBuildingListButton.setOnClickListener {
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToBuildingListFragment())
        }

        return binding.root
    }
}