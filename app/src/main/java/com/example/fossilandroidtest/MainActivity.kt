package com.example.fossilandroidtest

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.fossilandroidtest.model.Alarm

class MainActivity : AppCompatActivity() {
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button

    private val mockData = Alarm(alarmId = 1, hour = 10, minute = 45)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: Entry")
        setContentView(R.layout.activity_main)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnStart.setOnClickListener { mockData.scheduleAlarm(this.applicationContext) }

        btnStop.setOnClickListener { mockData.cancelAlarm(this.applicationContext) }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }


    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}