//package com.example.applyfestox.data.sharedpreferences
//
//import android.content.Context
//import androidx.compose.runtime.snapshots.SnapshotStateList
//import com.example.applyfestox.data.model.DetectionHistory
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//
//private const val PREF_NAME = "detection_history"
//private const val KEY_HISTORY = "history"
//
//fun saveDetectionHistory(context: Context, history: SnapshotStateList<com.example.applyfestox.screen.hasil.DetectionHistory>) {
//    val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//    val editor = sharedPreferences.edit()
//    val json = Gson().toJson(history)
//    editor.putString(KEY_HISTORY, json)
//    editor.apply()
//}
//
//fun loadDetectionHistory(context: Context): Collection<com.example.applyfestox.screen.hasil.DetectionHistory> {
//    val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//    val json = sharedPreferences.getString(KEY_HISTORY, null)
//    return if (json != null) {
//        val type = object : TypeToken<MutableList<DetectionHistory>>() {}.type
//        Gson().fromJson(json, type)
//    } else {
//        mutableListOf()
//    }
//}
