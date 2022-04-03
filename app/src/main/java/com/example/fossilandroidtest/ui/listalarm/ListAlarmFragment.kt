package com.example.fossilandroidtest.ui.listalarm

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fossilandroidtest.R
import com.example.fossilandroidtest.common.Constant
import com.example.fossilandroidtest.common.Event
import com.example.fossilandroidtest.databinding.FragmentListAlarmBinding
import com.example.fossilandroidtest.model.Alarm
import com.example.fossilandroidtest.ui.base.BaseFragment
import com.example.fossilandroidtest.ui.listalarm.adapter.AlarmAdapter
import com.example.fossilandroidtest.ui.listalarm.adapter.AlarmItemDecoration

class ListAlarmFragment : BaseFragment(R.layout.fragment_list_alarm) {

   private lateinit var alarmAdapter: AlarmAdapter
   private lateinit var binding: FragmentListAlarmBinding
   private val viewModel: ListAlarmViewModel by viewModels()

   override val titleScreen: String
      get() = Constant.LIST_ALARM_TITLE_SCREEN

   override val isHideBackButton: Boolean
      get() = true

   companion object {
       private val TAG = ListAlarmFragment::class.simpleName
   }

   override fun initInstance() {
      Log.i(TAG, "initInstance: Entry")
      alarmAdapter = AlarmAdapter(onEnable = ::onToggleEnable)
      viewModel.alarmRepository = repo
   }

   override fun initView(view: View) {
      Log.i(TAG, "initView: Entry")
      binding = FragmentListAlarmBinding.bind(view).apply {
         lifecycleOwner = this@ListAlarmFragment
         fragment = this@ListAlarmFragment
         vm = viewModel

         rvAlarm.run {
            if (itemDecorationCount == 0) addItemDecoration(AlarmItemDecoration(resources.getDimensionPixelOffset(R.dimen.dimen_15dp)))
            adapter = alarmAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
         }
      }
      viewModel.handleGetListAlarm()
   }

   override fun initObserver() {
      Log.i(TAG, "initObserver: Entry")
      viewModel.listAlarmResult.observe(viewLifecycleOwner) { data -> handleListAlarmData(data) }
   }

   override fun onViewClick(view: View) {
      Log.i(TAG, "onViewClick: Entry")
      with(binding) {
         when(view) {
            layoutHeader.btnAdd -> handleOnAddButtonClick()
         }
      }
   }

   /**
    * This function will update UI the new content of list if list was changed
    * @param data the data of list alarm
    */
   private fun handleListAlarmData(data: Event<List<Alarm>>) {
      Log.i(TAG, "handleListAlarmData: Entry")
      data.getContentIfNotHandled()?.let { listAlarm ->
         Log.d(TAG, "handleListAlarmData: Data is changed - Checking data of alarm list ${listAlarm.map { it.alarmId }}")
         alarmAdapter.setData(listAlarm)
      }
   }

   private fun handleOnAddButtonClick() {
      Log.i(TAG, "handleOnAddButtonClick: Entry")
      findNavController().navigate(R.id.action_listAlarmFragment_to_addAlarmFragment)
   }

   private fun onToggleEnable(alarm: Alarm) {
      Log.i(TAG, "onToggleEnable: Entry - Checking alarm $alarm")
      viewModel.handleUpdateStatusAlarm(alarm, requireContext().applicationContext)
   }

}