package com.example.r2d.utils

import android.app.Application
import android.content.Context


class R2DApplication : Application() {


    private val mInstance: R2DApplication? = null


    override fun onCreate() {
        super.onCreate()

        instance = this
        application = this

    }

    @Synchronized
    fun getInstance(): R2DApplication {
        return mInstance!!
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

    }

    companion object {

        private var activityVisible: Boolean = false

        lateinit var application: Application

        var instance: Context? = null
            private set
    }

}
