package com.garrymckee.powdermills.ui.splashscreen

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garrymckee.powdermills.domain.useragreement.UserAgreementRepository
import com.garrymckee.powdermills.ui.util.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val SPLASH_ANIMATION_DELAY = 3000L

class SplashScreenViewModel @ViewModelInject constructor(
    private val userAgreementRepository: UserAgreementRepository
) : ViewModel() {

    val continueToMapScreen = MutableLiveData<Event<Boolean>>()
    val continueToUserAgreementScreen = MutableLiveData<Event<Boolean>>()

    fun observeUserAgreement() = viewModelScope.launch {
        delay(SPLASH_ANIMATION_DELAY)
        userAgreementRepository
            .observeHasUserAgreed()
            .collect { userAgreement ->
                if (userAgreement.hasAgreed) {
                    continueToMapScreen.postValue(Event(true))
                } else {
                    continueToUserAgreementScreen.postValue(Event(true))
                }
            }
    }
}