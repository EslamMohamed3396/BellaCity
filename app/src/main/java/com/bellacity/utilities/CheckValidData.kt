package com.bellacity.utilities

import com.google.android.material.textfield.TextInputLayout

object CheckValidData {


    fun checkEditText(inputLayout: TextInputLayout): Boolean {
        return EditTextValidiation.validField(inputLayout)
    }


}
