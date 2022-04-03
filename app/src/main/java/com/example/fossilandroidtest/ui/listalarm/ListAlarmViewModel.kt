package com.example.fossilandroidtest.ui.listalarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fossilandroidtest.common.Event
import com.example.fossilandroidtest.model.Alarm
import com.example.fossilandroidtest.respository.AlarmRepository

class ListAlarmViewModel : ViewModel() {
    private val _listAlarmResult = MutableLiveData<Event<List<Alarm>>>()
    val listAlarmResult: LiveData<Event<List<Alarm>>>
        get() = _listAlarmResult

    private var _listAlarm = mutableListOf<Alarm>()
    val listAlarm: List<Alarm>
        get() = _listAlarm

    lateinit var alarmRepository: AlarmRepository

    init {
        _listAlarm = mutableListOf(
            Alarm(1, "aaaa", 16, 22, isEnable = true, isRepeatMon = true, isRepeatTue = true, isRepeatWed = true, isRepeatThu = true, isRepeatFri = true, isRepeatSat = true, isRepeatSun = true, createdTime = 1L),
            Alarm(2, "", 4, 3, isEnable = true, isRepeatMon = false, isRepeatTue = true, isRepeatWed = true, isRepeatThu = true, isRepeatFri = true, isRepeatSat = true, isRepeatSun = true, createdTime = 1L),
            Alarm(3, "1111", 8, 2, isEnable = true, isRepeatMon = true, isRepeatTue = true, isRepeatWed = false, isRepeatThu = true, isRepeatFri = true, isRepeatSat = true, isRepeatSun = true, createdTime = 1L)
        )
    }

    fun setData(){
        _listAlarmResult.value = Event(_listAlarm)
    }

    companion object {
        private val TAG = ListAlarmViewModel::class.simpleName
    }
}