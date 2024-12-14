//package com.example.applyfestox.data.datastore
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.booleanPreferencesKey
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.stringPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context
//
//object PreferencesKey {
//    val STATUS_LOGIN_KEY = booleanPreferencesKey("status_login")
//}
//
//class DataStore(private val context: Context) {
//
//    companion object {
//        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("StatusLogin")
//    }
//
//    val getStatusLogin: Flow<Boolean> = context.dataStore.data.map { preferences ->
//        preferences[PreferencesKey.STATUS_LOGIN_KEY] ?: false
//    }
//
//    suspend fun saveStatus(isLogin: Boolean) {
//        context.dataStore.edit { preferences ->
//            preferences[PreferencesKey.STATUS_LOGIN_KEY] = isLogin
//        }
//    }
//
//    suspend fun clearStatus() {
//        context.dataStore.edit { preferences ->
//            preferences.remove(PreferencesKey.STATUS_LOGIN_KEY)
//        }
//    }
//}
//
//val Context.dataStore by preferencesDataStore(name = "profile_prefs")
//
//object ProfileDataStore {
//    private const val PROFILE_IMAGE_KEY = "profile_image_uri"
//
//    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile_data_store")
//
//    val profileImageUri: Flow<String?> = context.dataStore.data
//        .map { preferences ->
//            preferences[PreferencesKeys.PROFILE_IMAGE_URI]
//        }
//
//    suspend fun setProfileImageUri(context: Context, uri: String) {
//        context.dataStore.edit { preferences ->
//            preferences[PreferencesKeys.PROFILE_IMAGE_URI] = uri
//        }
//    }
//
//    private object PreferencesKeys {
//        val PROFILE_IMAGE_URI = stringPreferencesKey(PROFILE_IMAGE_KEY)
//    }
//}
