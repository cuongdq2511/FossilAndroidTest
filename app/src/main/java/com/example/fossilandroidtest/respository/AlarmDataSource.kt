package com.example.fossilandroidtest.respository

import com.example.fossilandroidtest.model.Alarm

interface AlarmDataSource {

    suspend fun getListAlarm(): List<Alarm>
    suspend fun insertAlarm(alarm: Alarm): Long
    suspend fun updateAlarm(alarm: Alarm)

}