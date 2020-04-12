package com.masterAljAAR.films
import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class FilmAdvisor: Application() {

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }
}