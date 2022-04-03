package com.example.fossilandroidtest.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.example.fossilandroidtest.common.Constant
import com.example.fossilandroidtest.respository.AlarmRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: Entry")
        initRepository()
        initNotificationChannel()
    }

    /**
     * This function will init channel for notification
     */
    private fun initNotificationChannel() {
        Log.i(TAG, "initNotificationChannel: Entry")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Constant.CHANNEL_ID, Constant.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    /**
     * This function will init database to handle data
     */
    private fun initRepository() {
        Log.i(TAG, "initRepository: Entry")
        AlarmRepository.initDatabase(this)
    }

    companion object {
        private val TAG = App::class.simpleName
    }
}