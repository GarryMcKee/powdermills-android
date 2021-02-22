package com.garrymckee.powdermills

import android.app.Application
import com.testfairy.TestFairy
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PowderMillsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        //TODO add to build config
        TestFairy.begin(this, "SDK-E9JWuxYB")
    }
}