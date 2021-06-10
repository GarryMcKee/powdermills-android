package com.ballincollig.powdermills.domain.useragreement

import kotlinx.coroutines.flow.Flow

interface UserAgreementRepository {
    suspend fun observeHasUserAgreed(): Flow<UserAgreement>

    suspend fun updateUserAgreement(userAgreement: UserAgreement)
}