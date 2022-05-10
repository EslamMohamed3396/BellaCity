package com.bellacity.utilities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.R
import com.bellacity.databinding.CongrtsDialogBinding
import com.bellacity.databinding.LogoutDialogBinding
import com.bellacity.databinding.RecyclerSearchDialogBinding
import com.bellacity.databinding.SomethingWrongBinding
import timber.log.Timber


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
        retryListener: () -> Unit
    ) {
        val view: SomethingWrongBinding = SomethingWrongBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        )

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
    }

    fun showGeneraCongrts(
        context: Context,
        congrtsListener: () -> Unit,

        totalPoints: String
    ) {
        val view: CongrtsDialogBinding = CongrtsDialogBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        )
        val alertDialog = AlertDialog.Builder(context).apply {
            setView(view.root)
        }.create()
        if (alertDialog != null && alertDialog.isShowing) {
            alertDialog.dismiss()
        }
        alertDialog.apply {

            setCancelable(false)

            view.title.text = totalPoints

            view.retryBtn.setOnClickListener {
                congrtsListener.invoke()
                dismiss()
            }


            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()
    }

    var alertDialog: AlertDialog? = null
    fun <T : RecyclerView.ViewHolder?> showRecycler(
        context: Context,
        adabter: RecyclerView.Adapter<T>
    ) {
        val view: RecyclerSearchDialogBinding = RecyclerSearchDialogBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        )
        if (alertDialog != null && alertDialog?.isShowing == true) {
            alertDialog?.dismiss()
        }
        alertDialog = AlertDialog.Builder(context).apply {
            setView(view.root)
        }.create()

        Timber.d("${adabter.itemCount}}")

        alertDialog?.apply {
            view.rvAdapter.adapter = adabter
            setCancelable(false)
            view.imClose.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    fun dismissRecyclerDialog() {
        if (alertDialog != null && alertDialog?.isShowing == true) {
            alertDialog?.dismiss()
        }
    }

    fun showLogout(
        context: Context,
        logoutListener: () -> Unit
    ) {
        val view: LogoutDialogBinding = LogoutDialogBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        )
        val alertDialog = AlertDialog.Builder(context).apply {
            setView(view.root)
        }.create()

        alertDialog.apply {


            setCancelable(false)

            view.logoutBtn.setOnClickListener {
                logoutListener.invoke()
                dismiss()
            }
            view.cancelBtn.setOnClickListener {
                dismiss()
            }


            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()
    }
}
