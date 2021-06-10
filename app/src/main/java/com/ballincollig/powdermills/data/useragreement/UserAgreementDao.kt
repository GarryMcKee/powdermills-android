package com.ballincollig.powdermills.data.useragreement

import com.ballincollig.powdermills.domain.useragreement.UserAgreement
import kotlinx.coroutines.flow.Flow

interface UserAgreementDao {
    suspend fun observeHasUserAgreed(): Flow<UserAgreement>
    suspend fun updateUserAgreement(userAgreement: UserAgreement)
}