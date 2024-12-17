package com.example.applyfestox.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.applyfestox.data.sqlite.User

class LoginSessionHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun saveLoginSession(user: User) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putInt(KEY_USER_ID, user.id)
        editor.putString(KEY_EMAIL, user.email)
        editor.putString(KEY_NAME, user.name)
        editor.putString(KEY_PHOTO, user.photo) // Simpan path foto dengan benar
        editor.apply()
    }

    fun getLoggedInUser(): User? {
        if (!sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            return null
        }
        return User(
            id = sharedPreferences.getInt(KEY_USER_ID, -1),
            email = sharedPreferences.getString(KEY_EMAIL, "") ?: "",
            name = sharedPreferences.getString(KEY_NAME, "") ?: "",
            birthDate = "",
            photo = sharedPreferences.getString(KEY_PHOTO, "")
        )
    }


    fun clearLoginSession() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val SHARED_PREF_NAME = "login_session"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_EMAIL = "email"
        private const val KEY_NAME = "name"
        private const val KEY_PHOTO = "photo"
    }
}