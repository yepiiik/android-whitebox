package com.floredo.whitebox.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * This view model ensures that application preserve data on lifecycle changes (e.g. display rotation)
 * It is extended with additional functionality to avoid repeating code in custom view models
 */
abstract class BaseViewModel : ViewModel() {

    // Common state for all screens
    val isLoading = MutableStateFlow(false)
    val errorMessage = MutableStateFlow<String?>(null)

    protected fun showError(message: String) {
        errorMessage.value = message
    }
}