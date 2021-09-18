package com.bellacity.utilities

import android.app.Application
import android.content.Context
import com.bellacity.BuildConfig
import timber.log.Timber


class App : Application() {

    companion object {
        private var mContext: App? = null

        fun getContext(): Context? {
            return mContext
        }

    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}