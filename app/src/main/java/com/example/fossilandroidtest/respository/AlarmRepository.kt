package com.example.fossilandroidtest.respository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.fossilandroidtest.database.AlarmDatabase
import com.example.fossilandroidtest.database.dao.AlarmDao
import com.example.fossilandroidtest.model.Alarm

class AlarmRepository {
    companion object {
        private val TAG = AlarmRepository::class.simpleName

        private lateinit var alarmDao: AlarmDao

        private var alarmsLiveData: MutableLiveData<List<Alarm>> = MutableLiveData(listOf())

        private lateinit var db: AlarmDatabase

        fun initDatabase(application: Application) {
            db = AlarmDatabase.getInstance(application.applicationContext)
            alarmDao = db.getAlarmDao()
        }

        suspend fun getListAlarm() {
            alarmDao.getListAlarm()
        }

        suspend fun insertAlarm(alarm: Alarm): Long {
            return alarmDao.insertAlarm(alarm)
        }

        suspend fun updateAlarm(alarm: Alarm) {
            alarmDao.update(alarm)
        }
    }
}