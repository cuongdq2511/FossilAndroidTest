package com.example.fossilandroidtest.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.fossilandroidtest.common.Constant
import com.example.fossilandroidtest.service.AlarmService
import java.util.*

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Log.i(TAG, "onReceive: Entry")
        intent?.run {
            handleReceiveMessage(context, this)
        }
        Log.i(TAG, "onReceive: End")
    }

    /**
     * This function will start service
     */
    private fun startAlarmService(context: Context, intent: Intent) {
        Log.i(TAG, "startAlarmService: Start")
        val intentService = Intent(context, AlarmService::class.java)
        intentService.putExtra(Constant.ALARM_NAME, intent.getStringExtra(Constant.ALARM_NAME))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intent)
        }
        Log.i(TAG, "startAlarmService: End")
    }

    /**
     * This function handle after received message
     * If receive alarm because of repeating then check is today or not
     * @param context
     * @param intent
     */
    private fun handleReceiveMessage(context: Context, intent: Intent) {
        Log.i(TAG, "handleReceivedNormalAction: Entry")
        if (!intent.getBooleanExtra(Constant.REPEAT, false)) {
            Log.d(TAG, "handleReceivedNormalAction: No repeat alarm")
            startAlarmService(context, intent)
        } else {
            if (isToday(intent)) {
                Log.d(TAG, "handleReceivedNormalAction: is today")
                startAlarmService(context, intent)
            }
        }
        Log.i(TAG, "handleReceivedNormalAction: End")
    }

    /**
     * This function will check the current time with value of alarm
     * If today then start service
     * @param intent
     * @return Boolean
     */
    private fun isToday(intent: Intent): Boolean {
        Log.i(TAG, "checkIsToday: Entry")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        return when (calendar[Calendar.DAY_OF_WEEK]) {
            Calendar.SUNDAY -> Constant.SUNDAY
            Calendar.MONDAY -> Constant.MONDAY
            Calendar.TUESDAY -> Constant.TUESDAY
            Calendar.WEDNESDAY -> Constant.WEDNESDAY
            Calendar.THURSDAY -> Constant.THURSDAY
            Calendar.FRIDAY -> Constant.FRIDAY
            else -> Constant.SATURDAY
        }.let { intentName -> intent.getBooleanExtra(intentName, false) }
    }

    companion object {
        private val TAG = AlarmBroadcastReceiver::class.simpleName
    }
}