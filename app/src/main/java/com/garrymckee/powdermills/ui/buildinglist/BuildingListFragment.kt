package com.garrymckee.powdermills.ui.buildinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.garrymckee.powdermills.databinding.FragmentBuildingListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuildingListFragment : Fragment() {
    private val viewModel: BuildingListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBuildingListBinding.inflate(inflater, container, false)

        val buildingListAdapter = BuildingListAdapter()
        binding.buildingList.layoutManager = LinearLayoutManager(context)
        binding.buildingList.adapter = buildingListAdapter
        binding.lifecycleOwner = this
        subscribeToBuildingList(buildingListAdapter)
        viewModel.observeBuildings()
        return binding.root
    }

    private fun subscribeToBuildingList(adapter: BuildingListAdapter) {
        viewModel.buildingList.observe(viewLifecycleOwner, Observer {buildings ->
            buildings.map {
                it.mapToBuildingListUIModel()
            }.let {
                adapter.data = it
                adapter.notifyDataSetChanged()
            }
        })
    }
}