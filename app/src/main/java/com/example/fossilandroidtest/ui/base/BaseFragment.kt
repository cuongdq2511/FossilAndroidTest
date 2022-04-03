package com.example.fossilandroidtest.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.example.fossilandroidtest.R

abstract class BaseFragment(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

    open val titleScreen: String = ""
    open val isHideBackButton: Boolean = false
    open val isHideAddButton: Boolean = false

    open fun initInstance() {}
    open fun initView(view: View) {}
    open fun initObserver() {}
    open fun initListener() {}

    abstract fun onViewClick(view: View)

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