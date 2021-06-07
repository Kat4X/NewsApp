package com.fox.alyxnews

import android.content.Context
import android.content.SharedPreferences
import androidx.multidex.MultiDexApplication
import timber.log.Timber

class App: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        sp = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    companion object {
        private const val APP_PREFERENCES = "settings"
        private const val URL: String = "url"
        private lateinit var sp: SharedPreferences

        fun setUrl(url: String) {
            sp.edit().putString(URL, url).apply()
        }

        fun getUrl(): String = sp.getString(URL, "url").toString()
    }
}