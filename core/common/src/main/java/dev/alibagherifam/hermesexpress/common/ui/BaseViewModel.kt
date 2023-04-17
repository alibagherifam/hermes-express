package dev.alibagherifam.hermesexpress.common.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.io.IOException

@Suppress("PropertyName")
abstract class BaseViewModel<State>(initialState: State) : ViewModel() {
    protected val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<State> get() = _uiState

    protected abstract fun handleIOException(exception: Throwable)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable is IOException) {
            handleIOException(throwable)
        } else {
            throw throwable
        }
    }

    val safeScope = viewModelScope + exceptionHandler

    inline fun safeLaunch(crossinline block: suspend () -> Unit) {
        safeScope.launch { block() }
    }
}
