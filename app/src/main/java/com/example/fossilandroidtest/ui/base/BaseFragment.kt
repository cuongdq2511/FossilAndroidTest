package com.example.fossilandroidtest.ui.base

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.example.fossilandroidtest.R
import com.example.fossilandroidtest.database.AlarmDataSourceImpl
import com.example.fossilandroidtest.database.AlarmDatabase
import com.example.fossilandroidtest.respository.AlarmRepository

abstract class BaseFragment(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

    private lateinit var db : AlarmDatabase
    lateinit var repo : AlarmRepository

    open val titleScreen: String = ""
    open val isHideBackButton: Boolean = false
    open val isHideAddButton: Boolean = false

    open fun initInstance() {}
    open fun initView(view: View) {}
    open fun initObserver() {}
    open fun initListener() {}

    abstract fun onViewClick(view: View)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: Entry")
        db = AlarmDatabase.getInstance(requireContext().applicationContext)
        repo = AlarmRepository.getInstance(AlarmDataSourceImpl.getInstance(db.getAlarmDao()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance()
        initView(view)
        initObserver()
        initListener()
    }
    companion object {
        private val TAG = BaseFragment::class.java.simpleName
    }
}