package com.tripian.gyg.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.tripian.gyg.util.OnBackPressListener

abstract class BaseFragment<VB : ViewBinding> : Fragment(), OnBackPressListener {

    abstract val binding: (LayoutInflater) -> VB

    protected lateinit var vi: VB

    open fun setListeners() {}
    open fun setReceivers() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::vi.isInitialized) {
            vi = binding.invoke(layoutInflater)
        }

        container?.removeView(vi.root)
        return vi.root
    }

    override fun onResume() {
        if (activity is BaseActivity<*>) {
            /**
             * Activity'nin onBackPress methoduna register olunur.
             *
             */
            if (isBackEnable()) {
                (activity as BaseActivity<*>).onBackPressListener = this
            }
        }

        super.onResume()
    }

    override fun onPause() {
        if (activity is BaseActivity<*>) {
            /**
             * Activity'nin onBackPress methodundan unregister olunur.
             */
            if (isBackEnable()) {
                (activity as BaseActivity<*>).onBackPressListener = null
            }
        }

        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setReceivers()
    }

    fun goBack() {
        requireActivity().onBackPressed()
    }

    protected fun setFragmentResult(requestKey: String, result: Bundle) {
        requireActivity().supportFragmentManager.setFragmentResult(requestKey, result)
    }

    protected fun setFragmentResultListener(
        requestKey: String,
        listener: FragmentResultListener
    ) {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            requestKey,
            this,
            listener
        )
    }

    override fun isBackEnable() = false

    override fun onBackPressed() = true

    fun showToast(msg: String) {
        if (activity != null) {
            (activity as BaseActivity<*>).showToast(msg)
        }
    }
}
