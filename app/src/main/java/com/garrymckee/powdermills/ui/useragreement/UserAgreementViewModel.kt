package com.garrymckee.powdermills.useragreement

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garrymckee.powdermills.domain.useragreement.UserAgreementRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserAgreementViewModel @ViewModelInject constructor(
    private val userAgreementRepository: UserAgreementRepository
) : ViewModel() {
    val isAgreed = MutableLiveData<Boolean>()

    fun checkIfUserHasAgreed() =
        viewModelScope.launch {
            userAgreementRepository
                .observeHasUserAgreed()
                .collect { userAgreement ->
                    isAgreed.postValue(userAgreement.hasAgreed)
                }
        }
}