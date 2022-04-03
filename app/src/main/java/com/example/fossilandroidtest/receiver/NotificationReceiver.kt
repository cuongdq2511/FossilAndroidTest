package com.example.fossilandroidtest.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.fossilandroidtest.common.Constant
import com.example.fossilandroidtest.model.Alarm
import com.example.fossilandroidtest.service.AlarmService
import java.util.*

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(ctx: Context, intent: Intent?) {
        Log.i(TAG, "onReceive: Entry")
        intent?.run {
            if (intent.action == null) return
            when(intent.action) {
                Constant.DISMISS_ACTION -> handleDismissAction(ctx.applicationContext)
                Constant.SNOOZE_ACTION -> handleSnoozeAction(ctx.applicationContext)
            }
        }
    }

    /**
     * This function will stop alarm service then destroy this activity
     * @param context
     */
    private fun handleSnoozeAction(context: Context) {
        Log.i(TAG, "handleSnoozeAction: Entry")
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            add(Calendar.MINUTE, Constant.SNOOZE_MINUTE_DEFAULT)
        }
        Alarm(alarmId = Random().nextInt(Int.MAX_VALUE), hour = calendar.get(Calendar.HOUR_OF_DAY), minute = calendar.get(Calendar.MINUTE)).scheduleAlarm(context)
        val intent = Intent(context, AlarmService::class.java)
        context.stopService(intent)
    }

    /**
     * This function will snooze alarm more few minutes when user click
     * Then it will start service again
     */
    private fun handleDismissAction(context: Context) {
        Log.i(TAG, "handleDismissAction: Entry")
        context.stopService(Intent(context, AlarmService::class.java))
    }

    companion object {
        private val TAG = NotificationReceiver::class.java.simpleName
    }
}