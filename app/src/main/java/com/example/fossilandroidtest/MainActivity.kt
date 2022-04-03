package com.example.fossilandroidtest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

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