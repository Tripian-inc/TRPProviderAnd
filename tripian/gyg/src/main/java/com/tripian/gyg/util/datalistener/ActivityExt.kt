package com.tripian.gyg.util.datalistener

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tripian.gyg.base.BaseDialogFragment
import com.tripian.gyg.base.BaseActivity
import com.tripian.gyg.base.BaseBottomDialogFragment
import com.tripian.gyg.base.BaseFragment
import com.tripian.gyg.base.BaseViewModel
import kotlin.reflect.KClass

fun FragmentActivity.startActivity(kClass: KClass<out FragmentActivity>, bundle: Bundle? = null) {
    val intent = Intent(this, kClass.java)

    bundle?.let { intent.putExtras(it) }

    startActivity(intent)
}

@MainThread
inline fun <reified VM : BaseViewModel> BaseActivity<*>.injectVM(): Lazy<VM> {
    return viewModels<VM>().also {
        it.viewListener(this)
    }
}

@MainThread
inline fun <reified VM : BaseViewModel> BaseFragment<*>.injectVM(): Lazy<VM> {
    return viewModels<VM>().also {
        it.viewListener(this)
    }
}

@MainThread
inline fun <reified VM : BaseViewModel> BaseFragment<*>.injectActivityVM(): Lazy<VM> {
    return activityViewModels<VM>().also {
        it.viewListener(this)
    }
}

@MainThread
inline fun <reified VM : BaseViewModel> BaseDialogFragment<*>.injectVM(): Lazy<VM> {
    return viewModels<VM>().also {
        it.viewListener(this)
    }
}

@MainThread
inline fun <reified VM : BaseViewModel> BaseBottomDialogFragment<*>.injectVM(): Lazy<VM> {
    return viewModels<VM>().also {
        it.viewListener(this)
    }
}
