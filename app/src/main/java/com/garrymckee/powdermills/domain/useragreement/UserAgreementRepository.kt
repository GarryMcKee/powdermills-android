package com.garrymckee.powdermills.domain.useragreement

import kotlinx.coroutines.flow.Flow

interface UserAgreementRepository {
    suspend fun observeHasUserAgreed() : Flow<UserAgreement>
}