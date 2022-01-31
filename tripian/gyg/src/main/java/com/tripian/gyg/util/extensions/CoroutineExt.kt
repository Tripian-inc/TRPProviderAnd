package com.tripian.gyg.util.extensions

import androidx.lifecycle.viewModelScope
import com.tripian.gyg.base.BaseViewModel
import kotlinx.coroutines.*

fun BaseViewModel.launch(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(exceptionHandler, CoroutineStart.DEFAULT, block)
}

fun BaseViewModel.async(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.async(exceptionHandler, CoroutineStart.DEFAULT, block)
}