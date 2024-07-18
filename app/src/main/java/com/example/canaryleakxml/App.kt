package com.example.canaryleakxml

import android.app.Application
import leakcanary.LeakCanary

class App:Application() {
    override fun onCreate() {
        LeakCanary.config = LeakCanary.config.copy(dumpHeap = true)
        super.onCreate()
    }
}