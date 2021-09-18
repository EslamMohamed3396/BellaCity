package com.bellacity.utilities

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bellacity.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("visibilityViewText")
fun viewText(textView: View, text: String?) {
    if (!text.isNullOrBlank()) {
        textView.visibility = View.VISIBLE
    } else {
        textView.visibility = View.GONE
    }
}

@BindingAdapter("visibilityViewBoolean")
fun viewBoolean(textView: View, text: Boolean?) {
    if (text == true) {
        textView.visibility = View.VISIBLE
    } else {
        textView.visibility = View.GONE
    }
}

@BindingAdapter("timeConvert")
fun timeConvert(textView: TextView, timestamp: Long) {
    val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    textView.text = dateFormat.format(timestamp)
}

@BindingAdapter("app:changeDateFormat")
fun changeDateFormat(textView: TextView, dateString: String?) {
    //2021-09-01 08:40:04
    var result = ""
    if (!dateString.isNullOrEmpty()) {
        val formatterOld = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formatterNew = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        var date: Date? = null
        try {
            date = formatterOld.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (date != null) {
            result = formatterNew.format(date)
        }
        textView.text = "${textView.context.resources.getString(R.string.date)}  $result"
    }

}

@BindingAdapter("app:currentDate")
fun currentDate(textView: TextView, dateString: Date?) {
    //2021-09-01 08:40:04

    val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val formattedDate = df.format(dateString!!)
    textView.text = formattedDate

}
