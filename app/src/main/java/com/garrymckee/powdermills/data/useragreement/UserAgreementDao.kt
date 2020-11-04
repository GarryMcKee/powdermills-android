package com.garrymckee.powdermills.data.useragreement

import com.garrymckee.powdermills.domain.useragreement.UserAgreement
import kotlinx.coroutines.flow.Flow

interface UserAgreementDao {
    suspend fun observeHasUserAgreed(): Flow<UserAgreement>
    suspend fun updateUserAgreement(userAgreement: UserAgreement)
}