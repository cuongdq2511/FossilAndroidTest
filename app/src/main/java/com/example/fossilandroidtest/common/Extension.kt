package com.example.fossilandroidtest.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import java.util.Calendar.*

fun View.handleClearFocusEditText(editText: EditText, txtInput: TextInputLayout) {
    editText.clearFocus()
    txtInput.clearFocus()
    val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}