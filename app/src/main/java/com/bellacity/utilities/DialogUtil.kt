package com.bellacity.utilities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.bellacity.R
import com.bellacity.databinding.CongrtsDialogBinding
import com.bellacity.databinding.SomethingWrongBinding


object DialogUtil {
    private var dialog: Dialog? = null

    fun showDialog(context: Context?) {
        if (dialog != null && dialog?.isShowing!!) {
            return
        }
        dialog = Dialog(context!!)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setContentView(R.layout.dialog_loading)
        dialog?.setCancelable(false)
        dialog?.show()
    }

    fun dismissDialog() {
        if (dialog != null && dialog?.isShowing!!) {
            dialog?.dismiss()
        }
        dialog = null
    }

    fun showGeneralWithLogin(
        context: Context,
        retryListener: () -> Unit,
        view: SomethingWrongBinding = SomethingWrongBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        )
    ): AlertDialog {
        val alertDialog = AlertDialog.Builder(context).apply {
            setView(view.root)
        }.create()

        alertDialog.apply {


            setCancelable(false)


            view.retryBtn.setOnClickListener {
                retryListener.invoke()
                dismiss()
            }


            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()
        return alertDialog

    }

    fun showGeneraCongrts(
        context: Context,
        congrtsListener: () -> Unit,
        view: CongrtsDialogBinding = CongrtsDialogBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        ),
        totalPoints: String
    ): AlertDialog {
        val alertDialog = AlertDialog.Builder(context).apply {
            setView(view.root)
        }.create()

        alertDialog.apply {


            setCancelable(false)

            view.title.text = totalPoints

            view.retryBtn.setOnClickListener {
                congrtsListener.invoke()
                dismiss()
            }


            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()
        return alertDialog

    }
}
