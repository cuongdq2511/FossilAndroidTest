package com.example.fossilandroidtest.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.fossilandroidtest.common.Constant
import com.example.fossilandroidtest.service.AlarmService

class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.i(TAG, "onReceive: Entry")
        intent?.run {
            if (Intent.ACTION_BOOT_COMPLETED == action) handleAfterBootCompleted(context)
            else handleReceivedNormalAction(context, this)
        }
        Log.i(TAG, "onReceive: End")
    }

    private fun handleAfterBootCompleted(context: Context) {
        Log.i(TAG, "handleAfterBootCompleted: Entry")
        Log.i(TAG, "handleAfterBootCompleted: End")
    }

    private fun restartAlarmService(context: Context) {
        Log.i(TAG, "restartAlarmService: Entry")
        Log.i(TAG, "restartAlarmService: End")
    }

    private fun startAlarmService(context: Context, intent: Intent) {
        Log.i(TAG, "startAlarmService: Start")
        val intentService = Intent(context, AlarmService::class.java)
        intentService.putExtra(Constant.ALARM_TITLE, intent.getStringExtra(Constant.ALARM_TITLE))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intent)
        }
        Log.i(TAG, "startAlarmService: End")
    }

    private fun handleReceivedNormalAction(context: Context, intent: Intent) {
        Log.i(TAG, "handleReceivedNormalAction: Entry")
        startAlarmService(context, intent)
        Log.i(TAG, "handleReceivedNormalAction: End")
    }

    companion object {
        private val TAG = AlarmBroadcastReceiver::class.simpleName
    }
}