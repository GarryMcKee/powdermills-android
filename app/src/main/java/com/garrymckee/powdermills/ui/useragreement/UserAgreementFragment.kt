package com.garrymckee.powdermills.ui.useragreement

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.garrymckee.powdermills.R
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

        setupTermsAndConditionText()

        viewModel.continueToMapScreen.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                continueToNextScreen()
            }
        })

        return binding.root
    }

    private fun setupTermsAndConditionText() {
        /*
        The terms and conditions text uses some simple markup, we use the Html.fromHTML method to achieve this.
        For backwards compatibility a version check is required and will back to the older deprecated method if required.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.termsAndConditionsTextView.text =
                Html.fromHtml(
                    resources.getString(R.string.terms_and_conditions_html),
                    Html.FROM_HTML_MODE_COMPACT
                )
        } else {
            binding.termsAndConditionsTextView.text = Html.fromHtml(
                resources.getString(R.string.terms_and_conditions_html)
            )
        }

        binding.termsAndConditionsTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun continueToNextScreen() {
        findNavController()
            .navigate(UserAgreementFragmentDirections.actionUserAgreementFragmentToMapFragment())
    }
}