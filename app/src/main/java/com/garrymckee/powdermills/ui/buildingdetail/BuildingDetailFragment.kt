package com.garrymckee.powdermills.ui.buildingdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.garrymckee.powdermills.databinding.FragmentBuildingDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuildingDetailFragment : Fragment() {
    val args: BuildingDetailFragmentArgs by navArgs()
    private val viewModel: BuildingDetailViewModel by viewModels()
    private lateinit var binding: FragmentBuildingDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuildingDetailBinding.inflate(inflater, container, false)

        binding.backButton.setOnClickListener { this.findNavController().popBackStack() }

        subscribeLiveData()
        viewModel.observeBuildingWithId(args.buildingName)

        return binding.root
    }

    private fun subscribeLiveData() {

        viewModel.carouselImageResIds.observe(viewLifecycleOwner, Observer(this::setupCarousel))

        viewModel.titleTextLiveData.observe(viewLifecycleOwner, Observer {
            binding.buildingTitle.text = it
        })

        viewModel.historyTextLiveData.observe(viewLifecycleOwner, Observer {
            binding.historyDetailText.text = it
        })

        viewModel.functionTextLiveData.observe(viewLifecycleOwner, Observer {
            binding.functionDetailText.text = it
        })

        viewModel.triviaTextLiveData.observe(viewLifecycleOwner, Observer {
            binding.triviaDetailText.text = it
        })
    }

    private fun setupCarousel(it: List<Int>) {
        binding.carouselView.setImageListener { position, imageView ->
            imageView.setImageResource(it[position])
        }
        binding.carouselView.pageCount = it.size
    }
}