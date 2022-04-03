package com.example.fossilandroidtest.ui.addalarm

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.example.fossilandroidtest.R
import com.example.fossilandroidtest.common.Constant
import com.example.fossilandroidtest.common.handleClearFocusEditText
import com.example.fossilandroidtest.databinding.FragmentAddAlarmBinding
import com.example.fossilandroidtest.ui.base.BaseFragment

class AddAlarmFragment : BaseFragment(R.layout.fragment_add_alarm) {
    private val viewModel: AddAlarmViewModel by viewModels()
    private lateinit var binding: FragmentAddAlarmBinding

    override val titleScreen: String
        get() = Constant.ADD_ALARM_TITLE_SCREEN
    override val isHideAddButton: Boolean
        get() = true

    override fun initInstance() {
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initView(view: View) {
        binding = FragmentAddAlarmBinding.bind(view).apply {
            lifecycleOwner = this@AddAlarmFragment
            fragment =  this@AddAlarmFragment
            vm = viewModel

            timePicker.setIs24HourView(true)

            view.setOnTouchListener { view, event ->
                handleTouchView(event, view)
                true
            }
        }
    }

    /**
     * This function will handle when user touch outside edit text
     * The application will remove focus of edit text and close keyboard
     * @param event
     * @param view
     */
    private fun handleTouchView(event: MotionEvent, view: View) {
        Log.i(TAG, "handleTouchView: Entry")
        binding.run {
            if (edtName.isFocused) {
                val outRect = Rect()
                edtName.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX as Int, event.rawY as Int)) {
                    view.handleClearFocusEditText(edtName, txtInputName)
                }
            }
        }
    }

    override fun initObserver() {
    }

    override fun initListener() {
    }

    override fun onViewClick(view: View) {
        with(binding) {
            when (view) {
                layoutHeader.btnBack -> handleOnBackPress()
                btnSetAlarm -> handleAddNewAlarm()
            }
        }
    }

    private fun handleAddNewAlarm() {
        Log.i(TAG, "handleAddNewAlarm: Entry")
    }

    private fun handleOnBackPress() {
        Log.i(TAG, "handleOnBackPress: Entry")
    }
    
    companion object {
        private val TAG = AddAlarmFragment::class.java.simpleName
    }

}