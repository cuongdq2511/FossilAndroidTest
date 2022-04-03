package com.example.fossilandroidtest.database

import com.example.fossilandroidtest.database.dao.AlarmDao
import com.example.fossilandroidtest.model.Alarm
import com.example.fossilandroidtest.respository.AlarmDataSource

class AlarmDataSourceImpl(private val alarmDao: AlarmDao): AlarmDataSource {

    companion object {
        private val TAG = AlarmDataSourceImpl::class.simpleName

        @Volatile
        private var INSTANCE: AlarmDataSourceImpl? = null

        fun getInstance(alarmDao: AlarmDao): AlarmDataSourceImpl = INSTANCE ?: synchronized(this) {
            INSTANCE ?: AlarmDataSourceImpl(alarmDao).also { INSTANCE = it }
        }
    }

    override suspend fun getListAlarm(): List<Alarm> {
        return alarmDao.getListAlarm()
    }

    override suspend fun insertAlarm(alarm: Alarm): Long {
        return alarmDao.insertAlarm(alarm)
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        alarmDao.update(alarm)
    }
}