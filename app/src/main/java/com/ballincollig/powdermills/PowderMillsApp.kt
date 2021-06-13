package com.ballincollig.powdermills

import android.app.Application
import com.testfairy.TestFairy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class PowderMillsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        //TODO add to build config
        TestFairy.begin(this, "SDK-E9JWuxYB")

        if(BuildConfig.DEBUG){
            Timber.plant(DebugTree())
        }
    }
}