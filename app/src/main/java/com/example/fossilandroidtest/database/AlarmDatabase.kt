package com.example.fossilandroidtest.database

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fossilandroidtest.common.Constant
import com.example.fossilandroidtest.database.dao.AlarmDao
import com.example.fossilandroidtest.model.Alarm

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
abstract class AlarmDatabase: RoomDatabase() {

    abstract fun getAlarmDao(): AlarmDao

    companion object {
        private val TAG = AlarmDatabase::class.java.simpleName

        @Volatile
        private var INSTANCE: AlarmDatabase? = null

        fun getInstance(applicationContext: Context): AlarmDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(applicationContext).also { INSTANCE = it }
        }

        private fun buildDatabase(applicationContext: Context) =
            Room.databaseBuilder(applicationContext, AlarmDatabase::class.java, Constant.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d(TAG, "Database has been created.")
                    }

                    override fun onOpen(@NonNull db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.d(TAG, "Database has been opened.")
                    }
                })
                .build()
    }
}