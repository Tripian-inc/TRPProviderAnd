package com.tripian.gyg.base

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.tripian.gyg.R

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

    abstract val binding: (LayoutInflater) -> VB

    protected lateinit var vi: VB

    open fun setListeners() {}
    open fun setReceivers() {}

    override fun getTheme(): Int = R.style.AppTheme_Transparent

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                if (isBackEnable()) {
                    !onBackPressed()
                } else {
                    false
                }
            } else {
                false
            }
        }

        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        vi = binding.invoke(layoutInflater)

        return vi.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setReceivers()
    }

    override fun onPause() {
        activity?.currentFocus?.clearFocus()
        super.onPause()
    }

    open fun isBackEnable() = false

    open fun onBackPressed() = true
}
