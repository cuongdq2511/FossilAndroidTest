package com.example.fossilandroidtest.ui.addalarm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fossilandroidtest.model.Alarm
import com.example.fossilandroidtest.respository.AlarmRepository
import kotlinx.coroutines.*
import java.util.*

class AddAlarmViewModel : ViewModel() {
    private val _alarm = MutableLiveData<Alarm>().apply {
        val calendar = Calendar.getInstance().apply { timeInMillis = System.currentTimeMillis() }
        value = Alarm(hour = calendar.get(Calendar.HOUR_OF_DAY), minute = calendar.get(Calendar.MINUTE))
    }
    val alarm: LiveData<Alarm>
        get() = _alarm

    private var job = Job()
        get() {
            if (field.isCancelled) field = Job()
            return field
        }

    /**
     * This function will handle when user click to add new alarm
     * Save object to database then get ID to schedule
     * Call onDone callback to update UI
     * @param onDone callback when all process was done
     */
    fun handleOnAddAlarm(onDone: () -> Unit) {
        Log.d(TAG, "handleOnAddAlarm: Entry")
        alarm.value?.let { alarm ->
            alarm.isEnable = true
            viewModelScope.launch(job) {
                launch(Dispatchers.IO) {
                    Log.d(TAG, "handleOnAddAlarm: Start scope")
                    val insertedId = addAlarm(alarm)
                    startScheduling(insertedId, alarm)
                    Log.d(TAG, "handleOnAddAlarm: End Scope")
                }.join()
                Log.d(TAG, "handleOnAddAlarm: Ready to callback")
                onDone.invoke()
            }
        }
    }

    /**
     * This function will start scheduling process
     * @param insertedId ID of alarm inserted
     * @param alarm handling alarm
     */
    private fun startScheduling(insertedId: Int, alarm: Alarm) {
        Log.i(TAG, "startScheduling: Entry")
        alarm.apply {
            alarmId = insertedId

        }
    }

    /**
     * This function will add new alarm to database then return ID for scheduling alarm
     * @return Inserted ID of alarm
     */
    private suspend fun addAlarm(alarm: Alarm) = AlarmRepository.insertAlarm(alarm).toInt()

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: Entry")
        job.cancel()
    }

    companion object {
        private val TAG = AddAlarmViewModel::class.java.simpleName
    }
}