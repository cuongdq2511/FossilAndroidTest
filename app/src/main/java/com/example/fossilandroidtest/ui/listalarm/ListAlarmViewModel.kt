package com.example.fossilandroidtest.ui.listalarm

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fossilandroidtest.common.Event
import com.example.fossilandroidtest.model.Alarm
import com.example.fossilandroidtest.respository.AlarmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListAlarmViewModel : ViewModel() {
    private val _listAlarmResult = MutableLiveData<Event<List<Alarm>>>()
    val listAlarmResult: LiveData<Event<List<Alarm>>>
        get() = _listAlarmResult

    private var _listAlarm = mutableListOf<Alarm>()
    val listAlarm: List<Alarm>
        get() = _listAlarm

    lateinit var alarmRepository: AlarmRepository


    /**
     * This function will get all alarm in database and post value to update UI
     */
    fun handleGetListAlarm() {
        Log.i(TAG, "getListAlarm: Entry")
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                Log.d(TAG, "getListAlarm: Start Scope")
                getListAlarm()
                Log.d(TAG, "getListAlarm: End Scope")
            }.join()
            Log.d(TAG, "getListAlarm: update value")
            _listAlarmResult.postValue(Event(_listAlarm))
        }
    }

    private suspend fun getListAlarm() {
        Log.i(TAG, "getListAlarm: Entry")
        _listAlarm = alarmRepository.getListAlarm().toMutableList()
    }

    /**
     * This function will update status enable of alarm into database
     * Based on status then cancel or schedule
     * @param alarm changed Alarm
     * @param context
     */
    fun handleUpdateStatusAlarm(alarm: Alarm, context: Context) {
        Log.i(TAG, "handleUpdateStatusAlarm: Entry - Checking status ${alarm.isEnable}")
        viewModelScope.launch(Dispatchers.IO){
            launch(Dispatchers.Main) {
                Log.d(TAG, "handleUpdateStatusAlarm: Start scope")
                handleScheduling(alarm, context)
                Log.d(TAG, "handleUpdateStatusAlarm: End scope")
            }.join()
            Log.d(TAG, "handleUpdateStatusAlarm: Database")
            updateAlarmIntoDatabase(alarm)
        }
    }

    /**
     * This function will cancel or scheduling alarm based on status
     * @param alarm
     * @param context
     */
    private fun handleScheduling(alarm: Alarm, context: Context) {
        Log.d(TAG, "handleScheduling: Entry")
        if (alarm.isEnable) {
            alarm.cancelAlarm(context)
        } else {
            alarm.scheduleAlarm(context)
        }
    }

    /**
     * This function will update object alarm into database
     * @param alarm
     */
    private suspend fun updateAlarmIntoDatabase(alarm: Alarm) {
        Log.i(TAG, "updateAlarmIntoDatabase: Entry - Checkin alarm $alarm")
        alarmRepository.updateAlarm(alarm)
    }

    companion object {
        private val TAG = ListAlarmViewModel::class.simpleName
    }
}