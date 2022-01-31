package com.tripian.gyg.base

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.MenuRes
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tripian.gyg.R

abstract class BaseBottomDialogFragment<VB : ViewBinding> : BottomSheetDialogFragment() {

    abstract val binding: (LayoutInflater) -> VB

    protected lateinit var vi: VB

    private var mBehavior: BottomSheetBehavior<FrameLayout>? = null

    override fun getTheme(): Int = R.style.AppTheme_BottomSheetDialog

    open fun setListeners() {}
    open fun setReceivers() {}

    @MenuRes
    open fun getMenuId() = -1

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        isCancelable = true

        bottomSheetDialog.setOnShowListener { dialog: DialogInterface? ->
            val dg = dialog as BottomSheetDialog
            val bottomSheet = dg.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        bottomSheetDialog.setCanceledOnTouchOutside(true)

        bottomSheetDialog.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                if (isBackEnable()) {
                    !onBackPressed()
                } else {
                    false
                }
            } else {
                false
            }
        }

        return bottomSheetDialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        container?.setBackgroundColor(Color.TRANSPARENT)
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

    protected fun goBack() {
        dismiss()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (e: Exception) {
        }
    }

    protected fun setFragmentResult(requestKey: String, result: Bundle) {
        requireActivity().supportFragmentManager.setFragmentResult(requestKey, result)
    }

    open fun isBackEnable() = false

    open fun onBackPressed() = true
}
