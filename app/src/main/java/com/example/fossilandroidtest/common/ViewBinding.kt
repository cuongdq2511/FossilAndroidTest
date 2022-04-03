package com.example.fossilandroidtest.common

import android.view.View
import androidx.databinding.BindingAdapter

object ViewBinding {

    @JvmStatic
    @BindingAdapter("gone")
    fun View.bindGone(shouldGone: Boolean) {
        visibility = if (shouldGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }


}