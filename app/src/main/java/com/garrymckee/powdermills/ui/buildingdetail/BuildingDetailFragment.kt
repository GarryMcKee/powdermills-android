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
import com.bumptech.glide.Glide
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
        viewModel.observeBuildingWithId(args.buildingId)

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

        viewModel.showFunFactsLiveData.observe(viewLifecycleOwner, Observer {
            binding.funFactsVisiblityGroup.visibility = View.VISIBLE
        })

        viewModel.hideFunFactsLiveData.observe(viewLifecycleOwner, Observer {
            binding.funFactsVisiblityGroup.visibility = View.GONE
        })
    }

    private fun setupCarousel(it: List<String>) {
        binding.carouselView.setImageListener { position, imageView ->
            Glide
                .with(binding.root.context)
                .load(it[position])
                .centerCrop()
                .into(imageView)
        }
        binding.carouselView.pageCount = it.size
    }
}