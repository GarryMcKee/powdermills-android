package com.ballincollig.powdermills.ui.useragreement

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ballincollig.powdermills.domain.useragreement.UserAgreement
import com.ballincollig.powdermills.domain.useragreement.UserAgreementRepository
import com.ballincollig.powdermills.ui.util.Event
import kotlinx.coroutines.launch

class UserAgreementViewModel @ViewModelInject constructor(
    private val userAgreementRepository: UserAgreementRepository
) : ViewModel() {

    val continueToMapScreen = MutableLiveData<Event<Boolean>>()

    fun updateUserAgreement(userAgreement: UserAgreement) =
        viewModelScope.launch {
            userAgreementRepository.updateUserAgreement(userAgreement)
            continueToMapScreen.postValue(Event(true))
        }
}