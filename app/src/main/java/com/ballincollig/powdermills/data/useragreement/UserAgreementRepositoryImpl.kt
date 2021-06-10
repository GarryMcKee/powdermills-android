package com.ballincollig.powdermills.data.useragreement

import com.ballincollig.powdermills.domain.useragreement.UserAgreement
import com.ballincollig.powdermills.domain.useragreement.UserAgreementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAgreementRepositoryImpl @Inject constructor(private val userAgreementDao: UserAgreementDaoImpl) :
    UserAgreementRepository {

    override suspend fun observeHasUserAgreed(): Flow<UserAgreement> =
        userAgreementDao.observeHasUserAgreed()

    override suspend fun updateUserAgreement(userAgreement: UserAgreement) =
        userAgreementDao.updateUserAgreement(userAgreement)
}