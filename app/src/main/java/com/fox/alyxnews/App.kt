package com.fox.alyxnews

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class App: Application() {

    override fun onCreate() {
        super.onCreate()

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