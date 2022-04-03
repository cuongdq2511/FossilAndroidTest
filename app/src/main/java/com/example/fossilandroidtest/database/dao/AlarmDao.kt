package com.example.fossilandroidtest.database.dao

import androidx.room.*
import com.example.fossilandroidtest.model.Alarm

@Dao
interface AlarmDao {
    @Query("SELECT * FROM AlarmTable")
    suspend fun getListAlarm(): List<Alarm>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: Alarm): Long

    @Update
    fun update(alarm: Alarm)
}