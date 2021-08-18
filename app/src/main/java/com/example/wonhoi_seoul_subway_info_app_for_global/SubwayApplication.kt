package com.example.wonhoi_seoul_subway_info_app_for_global

import android.app.Application
import com.example.wonhoi_seoul_subway_info_app_for_global.di.appModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SubwayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(
                if(BuildConfig.DEBUG) {
                    Level.DEBUG
                } else {
                    Level.NONE
                }
            )
            androidContext(this@SubwayApplication)
            modules(appModule)
        }
    }
}