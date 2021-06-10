package com.ballincollig.powdermills.ui.buildinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ballincollig.powdermills.databinding.BuildingListItemBinding
import com.bumptech.glide.Glide

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

        private var buildingId: Long = -1

        init {
            binding.root.setOnClickListener {
                binding.buildingName.text.let {
                    navigateToBuilding(binding.root, buildingId)
                }
            }
        }

        fun bind(buildingItem: BuildingListUIModel) {
            binding.buildingName.text = buildingItem.title
            buildingId = buildingItem.buildingId
            Glide
                .with(binding.root.context)
                .load(buildingItem.imageUrl)
                .centerCrop()
                .into(binding.buildingImage)
        }

        private fun navigateToBuilding(view: View, buildingId: Long) {
            view.findNavController().navigate(
                BuildingListFragmentDirections.actionBuildingListFragmentToBuildingDetailFragment(
                    buildingId
                )
            )
        }
    }
}

