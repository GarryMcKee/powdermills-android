package com.garrymckee.powdermills.ui.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.garrymckee.powdermills.R
import com.garrymckee.powdermills.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private val viewModel: SplashScreenViewModel by viewModels()
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        Glide.with(this).asGif().load(R.raw.loading).into(binding.animationImageView)


        viewModel.continueToMapScreen.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                continueToMapScreen()
            }
        })

        viewModel.continueToUserAgreementScreen.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                continueToUserAgreementScreen()
            }
        })

        viewModel.observeUserAgreement()

        return binding.root
    }

    private fun continueToMapScreen() {
        findNavController()
            .navigate(SplashFragmentDirections.actionSplashFragmentToMapFragment())
    }

    private fun continueToUserAgreementScreen() {
        findNavController()
            .navigate(SplashFragmentDirections.actionSplashFragmentToUserAgreementFragment())
    }
}