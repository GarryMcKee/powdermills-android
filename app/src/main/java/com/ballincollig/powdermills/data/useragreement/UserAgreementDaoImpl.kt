package com.ballincollig.powdermills.data.useragreement

import com.ballincollig.powdermills.data.SharedPreferencesStorage
import com.ballincollig.powdermills.domain.useragreement.UserAgreement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserAgreementDaoImpl @Inject constructor(private val sharedPreferencesStorage: SharedPreferencesStorage) :
    UserAgreementDao {

    override suspend fun observeHasUserAgreed(): Flow<UserAgreement> = flow<UserAgreement> {
        sharedPreferencesStorage
            .getUserAgreement()
            .apply {
                emit(UserAgreement(this))
            }
    }

    override suspend fun updateUserAgreement(userAgreement: UserAgreement) =
        sharedPreferencesStorage.setUserAgreement(userAgreement.hasAgreed)
}
