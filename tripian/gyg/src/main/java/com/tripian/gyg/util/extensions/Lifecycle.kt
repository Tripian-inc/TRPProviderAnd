package com.tripian.gyg.util.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by Semih Özköroğlu on 22.07.2019
 */
fun <T : Any, L : LiveData<T?>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit): Unit =
        liveData.observe(this, { t -> body(t) })