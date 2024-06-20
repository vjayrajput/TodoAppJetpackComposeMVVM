package com.app.todo

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
    }

}
