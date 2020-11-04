package com.garrymckee.powdermills.ui.useragreement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.garrymckee.powdermills.databinding.FragmentUserAgreementBinding
import com.garrymckee.powdermills.domain.useragreement.UserAgreement
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAgreementFragment : Fragment() {
    private val viewModel: UserAgreementViewModel by viewModels()
    private lateinit var binding: FragmentUserAgreementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserAgreementBinding.inflate(inflater, container, false)

        binding.agreeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.continueButton.isEnabled = isChecked
        }

        binding.continueButton.setOnClickListener {
            viewModel.updateUserAgreement(UserAgreement(true))
        }

        viewModel.continueToMapScreen.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                continueToNextScreen()
            }
        })

        return binding.root
    }

    private fun continueToNextScreen() {
        findNavController()
            .navigate(UserAgreementFragmentDirections.actionUserAgreementFragmentToMapFragment())
    }
}