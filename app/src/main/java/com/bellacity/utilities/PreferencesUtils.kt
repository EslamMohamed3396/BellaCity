package com.bellacity.utilities

import android.content.Context
import com.bellacity.data.model.userDataSharedPref.response.UserData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferencesUtils(val context: Context?) {


    private var mInstance: PreferencesUtils? = null

    @Synchronized
    fun getInstance(): PreferencesUtils? {
        if (mInstance == null) {
            mInstance = PreferencesUtils(context)
        }
        return mInstance
    }

    private val sharedPreferences =
        App.getContext()!!.getSharedPreferences(
            Constant.sharedPrefFile,
            Constant.PRIVATE_MODE
        )


    /// Boolean
    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    /// String
    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun getStringLanguage(key: String): String? {
        return sharedPreferences.getString(key, "en")
    }

    /// Integer
    fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int? {
        return sharedPreferences.getInt(key, 0)
    }

    /// Float
    fun putFloat(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun getFloatTextSize(key: String): Float? {
        return sharedPreferences.getFloat(key, 1.0f)
    }


    fun putUserData(key: String, user: UserData) {
        val gson = Gson()
        val json = gson.toJson(user)
        sharedPreferences.edit().putString(key, json).apply()
    }

    fun getUserData(key: String): UserData? {
        val gson = Gson()
        val json = sharedPreferences.getString(key, null) ?: return null

        val type = object : TypeToken<UserData>() {
        }.type

        return gson.fromJson(json, type)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}