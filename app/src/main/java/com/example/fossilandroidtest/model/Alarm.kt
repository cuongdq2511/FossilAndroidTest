package com.example.fossilandroidtest.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.fossilandroidtest.common.Constant
import com.example.fossilandroidtest.receiver.AlarmBroadcastReceiver
import java.util.*

data class Alarm(
    val alarmId: Int = -1,
    var hour: Int = -1,
    var minute: Int = -1
) {

    fun schedule(context: Context) {
        Log.i(TAG, "schedule: Entry - Checking Alarm: $this")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val calendar = initCalendar()

        val pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }


    private fun initCalendar() = Calendar.getInstance().apply {
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