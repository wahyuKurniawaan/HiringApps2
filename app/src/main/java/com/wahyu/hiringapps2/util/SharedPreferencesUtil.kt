package com.wahyu.hiringapps2.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil (context: Context) {
    private val PREF_NAME = "SharedPrefHiringApps"
    private var sharedpref: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sharedpref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedpref.edit()
    }

    fun put(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun put(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getString(key: String): String? = sharedpref.getString(key, null)
    fun getBoolean(key: String): Boolean = sharedpref.getBoolean(key, false)

    fun clear() {
        editor.clear()
            .apply()
    }
}

class KeySharedPreferences {

    companion object {

        const val PREF_IS_LOGIN = "PREF_IS_LOGIN"
        const val PREF_USERNAME = "PREF_USERNAME"
        const val PREF_PASSWORD = "PREF_PASSWORD"
        const val PREF_EMAIL = "PREF_EMAIL"
        const val PREF_FIRST_NAME = "PREF_FIRST_NAME"
        const val PREF_LAST_NAME = "PREF_LAST_NAME"
        const val PREF_TOKEN = "PREF_TOKEN"
    }
}