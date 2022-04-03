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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: Entry")
        setContentView(R.layout.activity_main)
    }


    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}