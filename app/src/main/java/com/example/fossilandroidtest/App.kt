package com.example.fossilandroidtest

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.example.fossilandroidtest.common.Constant

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: Entry")
        initNotificationChannel()
    }

    private fun initNotificationChannel() {
        Log.i(TAG, "initNotificationChannel: Entry")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Constant.CHANNEL_ID, Constant.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    companion object {
        private val TAG = App::class.simpleName
    }
}