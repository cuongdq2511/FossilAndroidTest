package com.example.fossilandroidtest.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.fossilandroidtest.common.Constant
import com.example.fossilandroidtest.receiver.AlarmBroadcastReceiver
import java.util.*

data class Alarm(
    val alarmId: Int = -1,
    var hour: Int = -1,
    var minute: Int = -1
) {

    /**
     * This function will set Alarm Manager for scheduling alarm by specific AlarmId
     * It will send broadcast message to AlarmBroadcastReceiver when time up
     * @param context
     */
    fun scheduleAlarm(context: Context) {
        Log.i(TAG, "schedule: Entry - Checking Alarm: $this")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, initCalendar().timeInMillis, pendingIntent)
        Toast.makeText(context, "Start alarm at $hour - $minute successlly!", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "schedule: End")
    }

    /**
     * This function will cancel AlarmManager by specific AlarmID
     * @param context
     */
    fun cancelAlarm(context: Context) {
        Log.i(TAG, "cancel: Entry - Checking Alarm: $this")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0)
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Cancel alarm at $hour - $minute successlly!", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "cancelAlarm: End")
    }

    /**
     * This function will get calendar with time set by user
     * If user set time before current time then we will set alarm for the next day
     * @return Calendar
     */
    private fun initCalendar(): Calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, Constant.DEFAULT_SECOND)
        set(Calendar.MILLISECOND, Constant.DEFAULT_MILI_SECOND)

        /* This will check time user set is over current time or not - If yes then increase one more day */
        if (timeInMillis <= System.currentTimeMillis()) {
            Log.d(TAG, "initCalendar: Is over current time")
            set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH) + 1)
        }
    }

    companion object {
        private val TAG = Alarm::class.simpleName
    }
}