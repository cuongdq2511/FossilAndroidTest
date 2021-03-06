package com.example.fossilandroidtest.model

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fossilandroidtest.R
import com.example.fossilandroidtest.common.Constant
import com.example.fossilandroidtest.receiver.AlarmBroadcastReceiver
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "AlarmTable")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var alarmId: Int = 0,
    var name: String = "",
    var hour: Int = -1,
    var minute: Int = -1,
    var isEnable: Boolean = false,
    var isRepeatMon: Boolean = false,
    var isRepeatTue: Boolean = false,
    var isRepeatWed: Boolean = false,
    var isRepeatThu: Boolean = false,
    var isRepeatFri: Boolean = false,
    var isRepeatSat: Boolean = false,
    var isRepeatSun: Boolean = false,
    var createdTime: Long = 0
) {

    /**
     * This function will set Alarm Manager for scheduling alarm by specific AlarmId
     * It will send broadcast message to AlarmBroadcastReceiver when time up
     * @param context
     */
    @SuppressLint("SimpleDateFormat")
    fun scheduleAlarm(context: Context) {
        Log.i(TAG, "schedule: Entry - Checking Alarm: $this")
        isEnable = true
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = getIntentScheduling(context)
        val calendar = getCalendar()
        val pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0)
        if (!isRepeat()) {
            Log.d(TAG, "scheduleAlarm: Is not repeat")
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            SimpleDateFormat("EEEE, MMMM d").format(calendar.time)
        } else {
            Log.d(TAG, "scheduleAlarm: Is repeat")
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, Constant.RUN_DAILY_MIL_TIME, pendingIntent)
            getSpecificRepeatDay()
        }.let { contentDay ->
            Toast.makeText(context, context.resources.getString(R.string.add_alarm, name, contentDay, hour, minute), Toast.LENGTH_SHORT).show()
        }
        Log.i(TAG, "schedule: End")
    }

    /**
     * This function will cancel AlarmManager by specific AlarmID
     * @param context
     */
    fun cancelAlarm(context: Context) {
        Log.i(TAG, "cancel: Entry - Checking Alarm: $this")
        isEnable = false
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(context, alarmId, getIntentScheduling(context), PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, context.resources.getString(R.string.cancel_alarm, name, hour, minute), Toast.LENGTH_SHORT).show()
        Log.i(TAG, "cancelAlarm: End")
    }

    /**
     * This function will return intent which set all data need to be used
     * @return Intent
     */
    private fun getIntentScheduling(context: Context) = Intent(context, AlarmBroadcastReceiver::class.java).apply {
        action = Constant.AlARM_RECEIVER_ACTION
        putExtra(Constant.ALARM_NAME, name)
        putExtra(Constant.REPEAT, isRepeat())
        putExtra(Constant.MONDAY, isRepeatMon)
        putExtra(Constant.TUESDAY, isRepeatTue)
        putExtra(Constant.WEDNESDAY, isRepeatWed)
        putExtra(Constant.THURSDAY, isRepeatThu)
        putExtra(Constant.FRIDAY, isRepeatFri)
        putExtra(Constant.SATURDAY, isRepeatSat)
        putExtra(Constant.SUNDAY, isRepeatSun)
    }


    /**
     * This function will get calendar with time set by user
     * If user set time before current time then we will set alarm for the next day
     * @return Calendar
     */
    private fun getCalendar(): Calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, Constant.DEFAULT_SECOND)
        set(Calendar.MILLISECOND, Constant.DEFAULT_MIL_SECOND)

        /* This will check time user set is over current time or not - If yes then increase one more day */
        if (timeInMillis <= System.currentTimeMillis()) {
            Log.d(TAG, "initCalendar: Is over current time")
            set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH) + 1)
        }
    }

    /**
     * This function will get time text
     */
    fun getTime() = String.format("%02d:%02d", hour, minute)

    /**
     * This function will check is alarm every in week or not
     */
    private fun isEveryDay() = isRepeatMon && isRepeatTue && isRepeatWed && isRepeatThu && isRepeatFri && isRepeatSat && isRepeatSun

    /**
     * This function will check alarm is set repeat or not
     */
    private fun isRepeat() = isRepeatMon || isRepeatTue || isRepeatWed || isRepeatThu || isRepeatFri || isRepeatSat || isRepeatSun

    /**
     * This function will get item day which set for repeat alarm
     */
    fun getDayText() = if (isEveryDay()) Constant.EVERY_DAY else if (!isRepeat()) "" else getSpecificRepeatDay()

    /**
     * This function will check name is empty or not
     */
    fun isBlankName() = name.isEmpty() || name.isBlank()

    /**
     * This function will get specific day set by user then return text show it
     * @return String - all day included
     */
    private fun getSpecificRepeatDay(): String {
        return with(Constant) {
            var days = ""
            if (isRepeatMon) days += "$MONDAY "
            if (isRepeatTue) days += "$TUESDAY "
            if (isRepeatWed) days += "$WEDNESDAY "
            if (isRepeatThu) days += "$THURSDAY "
            if (isRepeatFri) days += "$FRIDAY "
            if (isRepeatSat) days += "$SATURDAY "
            if (isRepeatSun) days += "$SUNDAY "
            Log.d(TAG, "getSpecificRepeatDay: Checking result $days")
            days.dropLast(1)
        }
    }


    companion object {
        private val TAG = Alarm::class.simpleName
    }
}