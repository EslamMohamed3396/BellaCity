package com.bellacity.utilities

import com.bellacity.R
import com.google.android.material.textfield.TextInputLayout
import java.util.*

object EditTextValidiation {

    fun validField(
        textInputLayout: TextInputLayout
    ): Boolean {
        val mName = textInputLayout.editText?.text.toString().lowercase(Locale.ROOT).trim()
        return if (mName.length > 3) {
            textInputLayout.error = null
            true
        } else {
            textInputLayout.error =
                textInputLayout.context.resources.getString(R.string.invalid)
            textInputLayout.editText?.requestFocus()
            false
        }
    }


}
