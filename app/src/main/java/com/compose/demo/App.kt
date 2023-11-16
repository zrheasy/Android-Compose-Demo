package com.compose.demo

import android.app.Application

/**
 *
 * @author zrh
 * @date 2023/11/13
 *
 */
class App : Application() {
    companion object {
        lateinit var INSTANCE: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}