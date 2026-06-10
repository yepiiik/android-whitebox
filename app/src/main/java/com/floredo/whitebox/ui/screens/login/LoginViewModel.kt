package com.floredo.whitebox.ui.screens.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.floredo.whitebox.ui.BaseViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : BaseViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val TAG = "LoginViewModel"
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            Log.d(TAG, "Auth state changed: ${firebaseAuth.currentUser?.email}")
            _currentUser.value = firebaseAuth.currentUser
        }
    }

    suspend fun signInWithGoogle(credential: AuthCredential) {
        _loading.value = true
        _error.value = null
        try {
            Log.d(TAG, "Signing in with credential...")
            val result = auth.signInWithCredential(credential).await()
            Log.d(TAG, "Sign in successful for user: ${result.user?.email}")
        } catch (e: Exception) {
            Log.e(TAG, "Sign in failed", e)
            _error.value = e.message ?: "Login failed"
        } finally {
            _loading.value = false
        }
    }
}
