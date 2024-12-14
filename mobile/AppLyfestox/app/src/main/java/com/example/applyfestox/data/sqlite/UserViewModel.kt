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

class UserViewModel(private val context: Context) : ViewModel() {

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
     */
    fun login(email: String, password: String): Boolean {
        var loginSuccessful = false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // Validate user credentials
                if (databaseHelper.loginUser(email, password)) {
                    // Fetch user data
                    val user = databaseHelper.getUserData(email)
                    user?.let {
                        // Save login session
                        loginSessionHelper.saveLoginSession(it)
                        _currentUser.value = it
                        loginSuccessful = true
                    }
                }
            }
        }
        return loginSuccessful
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
