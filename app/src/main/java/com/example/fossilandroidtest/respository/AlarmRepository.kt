package com.example.fossilandroidtest.respository

import com.example.fossilandroidtest.database.AlarmDataSourceImpl
import com.example.fossilandroidtest.model.Alarm

class AlarmRepository(private val alarmDataSource: AlarmDataSourceImpl): AlarmDataSource {

    companion object {

        private val TAG = AlarmRepository::class.java.simpleName

        @Volatile
        private var INSTANCE: AlarmRepository? = null

        fun getInstance(dataSource: AlarmDataSourceImpl): AlarmRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: AlarmRepository(dataSource).also { INSTANCE = it }
        }
    }

    override suspend fun getListAlarm(): List<Alarm> {
        return alarmDataSource.getListAlarm()
    }

    override suspend fun insertAlarm(alarm: Alarm): Long {
        return alarmDataSource.insertAlarm(alarm)
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        alarmDataSource.updateAlarm(alarm)
    }
}