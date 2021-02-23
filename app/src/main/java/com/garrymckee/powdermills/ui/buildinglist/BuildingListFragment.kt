package com.garrymckee.powdermills.ui.buildinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.garrymckee.powdermills.databinding.FragmentBuildingListBinding
import com.garrymckee.powdermills.domain.building.Building
import com.garrymckee.powdermills.ui.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuildingListFragment : Fragment() {
    private val viewModel: BuildingListViewModel by viewModels()
    private lateinit var binding: FragmentBuildingListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuildingListBinding.inflate(inflater, container, false)

        val buildingListAdapter = BuildingListAdapter()
        binding.buildingList.layoutManager = LinearLayoutManager(context)
        binding.buildingList.adapter = buildingListAdapter
        binding.lifecycleOwner = this
        subscribeClickListeners()
        subscribeToBuildingList(buildingListAdapter)
        viewModel.observeBuildings()
        return binding.root
    }

    private fun subscribeToBuildingList(adapter: BuildingListAdapter) {
        viewModel.buildingList.observe(viewLifecycleOwner, Observer { buildings ->
            buildings.map(Building::mapToBuildingListUIModel).let {
                adapter.data = it
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun subscribeClickListeners() {
        binding.showBuildingListButton
            .setOnSingleClickListener {
                findNavController()
                    .popBackStack()
            }
    }
}