package com.tripian.gyg.ui.dialog

import DialogContent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.tripian.gyg.base.BaseDialogFragment
import com.tripian.gyg.databinding.FrWarningBinding

/**
 * Created by semihozkoroglu on 11.03.2020.
 */
class FRWarning : BaseDialogFragment<FrWarningBinding>() {

    override val binding: (LayoutInflater) -> FrWarningBinding = FrWarningBinding::inflate

    var dgContent: DialogContent? = null

    companion object {
        fun newInstance(content: DialogContent) = FRWarning().apply {
            arguments = Bundle().apply {
                putParcelable("content", content)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dgContent = arguments?.getParcelable("content")!!

        dgContent?.let {
            with(it) {
                when {
                    titleRes != null -> vi.tvTitle.text = getString(titleRes)
                    titleText != null -> vi.tvTitle.text = titleText
                    else -> vi.tvTitle.isVisible = false
                }

                when {
                    contentRes != null -> vi.tvDescription.text = getString(contentRes)
                    contentText != null -> vi.tvDescription.text = contentText
                    else -> vi.tvDescription.isVisible = false
                }

                if (negativeRes != null) {
                    vi.btnNegative.isVisible = true
                    vi.btnNegative.text = getString(negativeRes)
                    vi.btnNegative.setOnClickListener {
                        negative?.invoke()
                        dismiss()
                    }
                } else {
                    vi.btnNegative.isVisible = false
                }

                if (positiveRes != null) {
                    vi.btnPositive.isVisible = true
                    vi.btnPositive.text = getString(positiveRes)
                    vi.btnPositive.setOnClickListener {
                        positive?.invoke()
                        dismiss()
                    }
                } else {
                    vi.btnPositive.isVisible = false
                }

                isCancelable = cancellable
            }
        }
    }
}