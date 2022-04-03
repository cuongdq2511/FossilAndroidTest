package com.example.fossilandroidtest.ui.listalarm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fossilandroidtest.common.Event
import com.example.fossilandroidtest.model.Alarm
import com.example.fossilandroidtest.respository.AlarmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
     * This function will get all alarm in database and show on UI
     */
    fun getListAlarm() {
        Log.i(TAG, "getListAlarm: Entry")
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                Log.d(TAG, "getListAlarm: Start Scope") 
                _listAlarm = alarmRepository . getListAlarm ().toMutableList()
                Log.d(TAG, "getListAlarm: End Scope")
            }.join()
            Log.d(TAG, "getListAlarm: update value")
            _listAlarmResult.value = Event(_listAlarm)
        }
    }

    companion object {
        private val TAG = ListAlarmViewModel::class.simpleName
    }
}