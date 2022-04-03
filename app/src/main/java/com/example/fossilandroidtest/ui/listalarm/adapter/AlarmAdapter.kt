package com.example.fossilandroidtest.ui.listalarm.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fossilandroidtest.databinding.ItemAlarmBinding
import com.example.fossilandroidtest.model.Alarm

class AlarmAdapter(private var listAlarm: List<Alarm> = mutableListOf(),private val onEnable: (Alarm) -> Unit) : RecyclerView.Adapter<AlarmViewHolder>() {

    fun setData(listAlarm: List<Alarm>) {
        this.listAlarm = listAlarm
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        return AlarmViewHolder(ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val item = listAlarm[position]
        Log.i(TAG, "onBindViewHolder: Entry - Checking Alarm ID ${item.alarmId}")
        with(holder) {
            onBind(item)
            onBindEvent(item, onEnable)
        }
    }

    override fun getItemCount() = listAlarm.size

    companion object {
        private val TAG = AlarmAdapter::class.java.simpleName
    }
}