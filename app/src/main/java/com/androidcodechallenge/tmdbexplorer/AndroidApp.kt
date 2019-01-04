package com.androidcodechallenge.tmdbexplorer

import android.app.Application
import com.androidcodechallenge.tmdbexplorer.injection.AppComponent
import com.androidcodechallenge.tmdbexplorer.injection.AppModule
import com.androidcodechallenge.tmdbexplorer.injection.DaggerAppComponent
import com.androidcodechallenge.tmdbexplorer.utilities.HuaweiTree
import io.paperdb.Paper
import timber.log.Timber


class AndroidApp : Application() {

    val component: AppComponent by lazy {

        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            val deviceManufacturer = android.os.Build.MANUFACTURER

            if (deviceManufacturer.toLowerCase().contains("huawei")) {
                Timber.plant(HuaweiTree())
            } else {
                Timber.plant(Timber.DebugTree())
            }
        }

        component.inject(this)
        Paper.init(this)
    }
}