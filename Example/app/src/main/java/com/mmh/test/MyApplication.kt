package com.mmh.test

import android.app.Application
import android.graphics.Color
import com.beinmobiles.idity.*

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // This is the equivalent of didFinishLaunchingWithOptions
        IDitySDK.initialize(
            clientKey = "CLIENT_KEY",
            recognitionLanguage = IDityLanguage.LATIN,
            tintColor = Color.RED
            )
    }
}