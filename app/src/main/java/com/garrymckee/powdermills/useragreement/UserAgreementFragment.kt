package com.garrymckee.powdermills.useragreement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.garrymckee.powdermills.databinding.FragmentBuildingListBinding
import com.garrymckee.powdermills.databinding.FragmentUserAgreementBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAgreementFragment : Fragment() {
    private val viewModel: UserAgreementViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUserAgreementBinding.inflate(inflater, container, false)

        return binding.root
    }
}