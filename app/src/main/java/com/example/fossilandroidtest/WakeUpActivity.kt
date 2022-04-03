package com.example.fossilandroidtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.fossilandroidtest.common.Constant
import com.example.fossilandroidtest.model.Alarm
import com.example.fossilandroidtest.service.AlarmService
import java.util.*

class WakeUpActivity : AppCompatActivity() {
    private lateinit var btnDismiss: Button
    private lateinit var btnSnooze: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wake_up)
        btnSnooze = findViewById(R.id.btnSnooze)
        btnDismiss = findViewById(R.id.btnDismiss)
        btnSnooze.setOnClickListener { handleSnoozeAlarm() }
        btnDismiss.setOnClickListener { handleDismissAlarm() }
    }

    /**
     * This function will stop alarm service then destroy this activity
     */
    private fun handleDismissAlarm() {
        Log.i(TAG, "handleDismissAlarm: Entry")
        applicationContext.stopService(Intent(applicationContext, AlarmService::class.java))
        finish()
    }

    /**
     * This function will snooze alarm more few minutes when user click
     * Then it will start service again
     */
    private fun handleSnoozeAlarm() {
        Log.i(TAG, "handleSnoozeAlarm: Entry")
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            add(Calendar.MINUTE, Constant.SNOOZE_MINUTE_DEFAULT)
        }
        Alarm(alarmId = Random().nextInt(Int.MAX_VALUE), hour = calendar.get(Calendar.HOUR_OF_DAY), minute = calendar.get(Calendar.MINUTE),).scheduleAlarm(applicationContext)
        val intent = Intent(applicationContext, AlarmService::class.java)
        applicationContext.stopService(intent)
        finish()
    }

    companion object {
        private val TAG = WakeUpActivity::class.java.simpleName
    }
}