package com.garrymckee.powdermills.buildinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garrymckee.powdermills.databinding.BuildingListItemBinding

class BuildingListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = listOf<BuildingListUIModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        BuildingListItemViewHolder(
            BuildingListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = (holder as BuildingListItemViewHolder).bind(data[position])

    override fun getItemCount() = data.size

    class BuildingListItemViewHolder(private val binding: BuildingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(buildingItem: BuildingListUIModel) {
            binding.buildingName.text = buildingItem.title
        }
    }
}

