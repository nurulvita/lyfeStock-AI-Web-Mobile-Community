package com.example.applyfestox.data.sqlite

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applyfestox.data.sharedpreferences.LoginSessionHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(
    private val context: Context
) : ViewModel() {

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    private val loginSessionHelper = LoginSessionHelper(context)
    private val databaseHelper = DatabaseHelper(context)

    init {
        loadUserData()
    }

    /**
     * Load user data from session and database
     */
    private fun loadUserData() {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                loginSessionHelper.getLoggedInUser()
            }
            _currentUser.value = user
        }
    }

    /**
     * Login user and save session
     * @return True if login successful, false otherwise
     */
    suspend fun loginAndGetDiagnosa(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            // Validasi login dan ambil userId
            val userId = databaseHelper.loginUser(email, password)
            if (userId != null) {
                // Login sukses, ambil data diagnosa
                val diagnosaList = databaseHelper.getDiagnosaData(userId)

                // Simpan sesi login dan diagnosa
                val user = databaseHelper.getUserData(email)
                user?.let {
                    loginSessionHelper.saveLoginSession(it)
                    _currentUser.value = it

                    // Update UI atau status dengan data diagnosa yang sudah diambil
                    // Bisa menggunakan _diagnosaList.value = diagnosaList jika memakai LiveData atau State
                    return@withContext true
                }
            }
            false // Login gagal
        }
    }



    /**
     * Logout user and clear session
     */
    fun logout() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loginSessionHelper.clearLoginSession()
            }
            _currentUser.value = null
        }
    }
}
