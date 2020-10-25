package com.garrymckee.powdermills.data

import com.garrymckee.powdermills.domain.useragreement.UserAgreement
import com.garrymckee.powdermills.domain.useragreement.UserAgreementRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserAgreementRepositoryImpl @Inject constructor() : UserAgreementRepository {
    override suspend fun observeHasUserAgreed(): Flow<UserAgreement> = flow {
        delay(1000)
        emit(UserAgreement(false))
    }
}