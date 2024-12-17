//package com.example.applyfestox.data.repository
//
//import android.content.Context
//import android.content.SharedPreferences
//import com.example.applyfestox.data.network.User
//
//class LoginSessionHelper(context: Context) {
//    private val sharedPreferences: SharedPreferences =
//        context.getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
//
//    fun saveLoginSession(user: User, token: String) {
//        sharedPreferences.edit()
//            .putString("USER_NAME", user.name)
//            .putString("USER_EMAIL", user.email)
//            .putString("TOKEN", token)
//            .apply()
//    }
//
//    fun getToken(): String? {
//        return sharedPreferences.getString("TOKEN", null)
//    }
//
//    fun clearSession() {
//        sharedPreferences.edit().clear().apply()
//    }
//}
