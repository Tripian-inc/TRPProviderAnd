package com.tripian.gyg.base

import DialogContent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import androidx.annotation.CallSuper
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentResultListener
import androidx.viewbinding.ViewBinding
import com.tripian.gyg.R
import com.tripian.gyg.ui.dialog.FRWarning
import com.tripian.gyg.util.DGLockScreen
import com.tripian.gyg.util.OnBackPressListener
import com.tripian.gyg.util.extensions.hideKeyboard
import com.tripian.gyg.util.extensions.showFragment
import com.tripian.gyg.util.fragment.FragmentFactory
import es.dmoral.toasty.Toasty

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    var onBackPressListener: OnBackPressListener? = null

    abstract val binding: (LayoutInflater) -> VB

    protected lateinit var vi: VB

    private var dgLockScreen: DGLockScreen? = null

    @MenuRes
    open fun getMenuId() = -1

    @CallSuper
    open fun onMenuCreated(menu: Menu) {
    }

    @CallSuper
    open fun showLoading() {
        if (dgLockScreen == null) {
            dgLockScreen = DGLockScreen(this)
        }

        dgLockScreen?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    @CallSuper
    open fun hideLoading() {
        dgLockScreen?.dismiss()
    }

    @CallSuper
    open fun showDialog(content: DialogContent) {
        showFragment(FragmentFactory.Builder(FRWarning.newInstance(content)).build())
    }

    open fun setListeners() {}
    open fun setReceivers() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vi = binding.invoke(layoutInflater).apply {
            setContentView(root)
        }

        setListeners()
        setReceivers()
    }

    fun showToast(msg: String) {
        Toasty.custom(this, msg, R.drawable.ic_info, R.color.white, 2000, true, false).show()
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    override fun onBackPressed() {
        if (onBackPressListener != null && onBackPressListener!!.isBackEnable()) {
            if (onBackPressListener!!.onBackPressed()) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        hideLoading()

        super.onDestroy()
    }

    protected fun setFragmentResult(requestKey: String, result: Bundle) {
        supportFragmentManager.setFragmentResult(requestKey, result)
    }

    protected fun setFragmentResultListener(requestKey: String, listener: FragmentResultListener) {
        supportFragmentManager.setFragmentResultListener(requestKey, this, listener)
    }
}
