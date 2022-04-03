package com.example.fossilandroidtest.ui.listalarm.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.fossilandroidtest.databinding.ItemAlarmBinding
import com.example.fossilandroidtest.model.Alarm

class AlarmViewHolder(private val binding: ItemAlarmBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(alarmItem: Alarm) {
        binding.run { item = alarmItem }
    }

    fun onBindEvent(alarmItem: Alarm, onEnable: (Alarm) -> Unit) {
        binding.scEnable.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked != alarmItem.isEnable) {
                onEnable.invoke(alarmItem)
            }
        }
    }

    companion object {
        private val TAG = AlarmViewHolder::class.java.simpleName
    }
}