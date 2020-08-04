package be.chaidev.chronote

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChronoteApp: Application() {


    lateinit var serviceLocator: ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator = ServiceLocator(applicationContext)
    }
}