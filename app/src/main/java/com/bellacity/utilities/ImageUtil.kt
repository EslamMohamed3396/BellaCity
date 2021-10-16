package com.bellacity.utilities

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


object ImageUtil {

    fun encodeImage(bm: Bitmap?): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bm?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun getBase64Image(bitmap: Bitmap): String? {
        try {
            val buffer: ByteBuffer = ByteBuffer.allocate(
                bitmap.rowBytes *
                        bitmap.height
            )
            bitmap.copyPixelsToBuffer(buffer)
            val data: ByteArray = buffer.array()
            return Base64.encodeToString(data, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


}