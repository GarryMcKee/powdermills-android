package com.garrymckee.powdermills.ui.buildinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.garrymckee.powdermills.databinding.BuildingListItemBinding

class BuildingListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = listOf<BuildingListUIModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        BuildingListItemViewHolder(
            BuildingListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as BuildingListItemViewHolder).bind(data[position])

    override fun getItemCount() = data.size

    class BuildingListItemViewHolder(private val binding: BuildingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                binding.buildingName.text.let {
                    navigateToBuilding(binding.root, it.toString())
                }
            }
        }

        fun bind(buildingItem: BuildingListUIModel) {
            binding.buildingName.text = buildingItem.title
            binding.buildingImage.setImageResource(buildingItem.imageResId)
        }

        private fun navigateToBuilding(view: View, buildingName: String) {
            view.findNavController().navigate(
                BuildingListFragmentDirections.actionBuildingListFragmentToBuildingDetailFragment(
                    buildingName
                )
            )
        }
    }
}

