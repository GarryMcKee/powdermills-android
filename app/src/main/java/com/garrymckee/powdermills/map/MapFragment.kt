package com.garrymckee.powdermills.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.garrymckee.powdermills.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentMapBinding = FragmentMapBinding.inflate(inflater)

        val model: MapViewModel by viewModels()

        binding.showBuildingListButton.setOnClickListener {
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToBuildingDetailFragment())
        }

        return binding.root
    }
}