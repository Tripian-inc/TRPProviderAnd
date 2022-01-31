package com.tripian.gyg.base

import DialogContent
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.tripian.gyg.R
import com.tripian.gyg.repository.model.ErrorResponse
import com.tripian.gyg.util.exception.ValidationException
import com.tripian.gyg.util.fragment.FragmentFactory
import dialog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    var _dialog = Channel<DialogContent?>()
    val dialog: Flow<DialogContent?> = _dialog.receiveAsFlow()

    private var _showToast = Channel<String?>()
    val showToast: Flow<String?> = _showToast.receiveAsFlow()

    private var _showLoading = Channel<Unit?>()
    val showLoading: Flow<Unit?> = _showLoading.receiveAsFlow()

    private var _hideLoading = Channel<Unit?>()
    val hideLoading: Flow<Unit?> = _hideLoading.receiveAsFlow()

    private var _goBack = Channel<Unit?>()
    val goBack: Flow<Unit?> = _goBack.receiveAsFlow()

    private var _finish = Channel<Unit?>()
    val finish: Flow<Unit?> = _finish.receiveAsFlow()

    private var _showFragment = Channel<FragmentFactory?>()
    val showFragment: Flow<FragmentFactory?> = _showFragment.receiveAsFlow()

    fun goBack() {
        viewModelScope.launch { _goBack.send(Unit) }
    }

    fun finish() {
        viewModelScope.launch { _finish.send(Unit) }
    }

    fun toast(text: () -> String) {
        viewModelScope.launch { _showToast.send(text.invoke()) }
    }

    fun loading(show: () -> Boolean) {
        if (show.invoke()) {
            viewModelScope.launch { _showLoading.send(Unit) }
        } else {
            viewModelScope.launch { _hideLoading.send(Unit) }
        }
    }

    fun showFragment(factory: FragmentFactory) {
        viewModelScope.launch { _showFragment.send(factory) }
    }

    val exceptionHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        when (e) {
            is SocketTimeoutException,
            is SSLHandshakeException,
            is UnknownHostException,
            is ConnectException -> {
                dialog {
                    titleResId = R.string.error_title
                    contentResId = R.string.connect_error_description
                }
            }
            is HttpException -> {
                e.response()?.errorBody()?.string()?.let {
                    try {
                        val error = Gson().fromJson(it, ErrorResponse::class.java)

                        dialog {
                            titleResId = R.string.error_title
                            contentText = error?.errors?.get(0)?.errorMessage ?: e.message
                        }
                    } catch (e: Exception) {
                        dialog {
                            titleResId = R.string.error_title
                            contentText = e.message ?: e.localizedMessage
                        }
                    }
                } ?: run {
                    dialog {
                        titleResId = R.string.error_title
                        contentText = e.message ?: e.localizedMessage
                    }
                }
            }
            is ValidationException -> {
                toast { e.message }
            }
            else -> {
                dialog {
                    titleResId = R.string.error_title
                    contentText = e.message ?: e.localizedMessage
//                    contentResId = R.string.generic_error_description
                }
            }
        }
    }
}