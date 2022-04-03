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

    @JvmStatic
    @BindingAdapter("dim")
    fun View.bindDim(shouldBeDimmed: Boolean) {
        alpha = if (shouldBeDimmed) {
            0.4f
        } else {
            1f
        }
    }
}