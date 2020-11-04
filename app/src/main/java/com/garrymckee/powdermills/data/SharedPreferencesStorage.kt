package com.garrymckee.powdermills.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

const val SHARED_PREFERENCES_NAME = "powder_mills_shared_preference"
const val USER_AGREEMENT_KEY = "userHasAgreedKey"

@Singleton
class SharedPreferencesStorage @Inject constructor(@ApplicationContext context: Context) {
    val prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    fun getUserAgreement(): Boolean {
        return prefs.getBoolean(USER_AGREEMENT_KEY, false)
    }

    fun setUserAgreement(hasUserAgreed: Boolean) {
        prefs.edit().putBoolean(USER_AGREEMENT_KEY, hasUserAgreed).commit()
    }
}