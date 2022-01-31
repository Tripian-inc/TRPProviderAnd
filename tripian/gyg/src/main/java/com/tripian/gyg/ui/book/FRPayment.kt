package com.tripian.gyg.ui.book

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.isVisible
import com.tripian.gyg.R
import com.tripian.gyg.base.BaseFragment
import com.tripian.gyg.databinding.FrPaymentBinding
import com.tripian.gyg.util.datalistener.injectVM
import com.tripian.gyg.util.extensions.observe
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by semihozkoroglu on 28.01.2022.
 */
class FRPayment : BaseFragment<FrPaymentBinding>() {

    override val binding: (LayoutInflater) -> FrPaymentBinding = FrPaymentBinding::inflate

    private val viewModel: FRPaymentVM by injectVM()

    companion object {
        fun newInstance(): FRPayment {
            return FRPayment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val years = ArrayList<String>()

        val today = Calendar.getInstance()
        val currentYear = today.get(Calendar.YEAR)

        for (i in 0 until 20) {
            today.set(Calendar.YEAR, currentYear + i)

            years.add("${today.get(Calendar.YEAR)}")
        }

        vi.expYear.adapter = ArrayAdapter(requireContext(), R.layout.item_spinner_textview, years)

        vi.tvTitle.text = BookingPageData.data.title
        vi.tvDate.text = BookingPageData.data.bookable?.datetime
        vi.tvOption.text = BookingPageData.data.bookable?.optionTitle
        vi.tvCount.text = BookingPageData.data.bookable?.categories?.joinToString { "${it.participants} x ${it.categoryName}" }

        viewModel.init()
    }

    override fun onResume() {
        super.onResume()

        (requireActivity() as ACBook).setPageTitle(R.string.page_title_payment)
    }

    override fun setListeners() {
        vi.etMonth.setOnClickListener {
            vi.expMonth.performClick()

            vi.expMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    vi.etMonth.setText((vi.expMonth.selectedView as TextView).text.toString())
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    vi.etMonth.setText((view as TextView).text.toString())
                }
            }
        }

        vi.etYear.setOnClickListener {
            vi.expYear.performClick()

            vi.expYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    vi.etMonth.setText((vi.expYear.selectedView as TextView).text.toString())
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    vi.etYear.setText((view as TextView).text.toString())
                }
            }
        }

        vi.btnPay.setOnClickListener {
            pay(vi.etNameOnCard.text.toString(), vi.etCardNumber.text.toString(), vi.etMonth.text.toString(), vi.etYear.text.toString(), vi.etCvv.text.toString())
        }
    }

    override fun setReceivers() {
        observe(viewModel.showImagesListener) {
            it?.forEach {
                when (it.code) {
                    0 -> vi.imVisa.isVisible = true
                    1 -> vi.imMaster.isVisible = true
                    2 -> vi.imAmex.isVisible = true
                    4 -> vi.imJcb.isVisible = true
                    5 -> vi.imDiscover.isVisible = true
                }
            }
        }
    }

    private fun pay(cardOnName: String, cardNumber: String, month: String, year: String, cvc: String) {
        var validationOk = true

        if (TextUtils.isEmpty(cardOnName)) {
            validationOk = false
            showToast(getString(R.string.error_wrong_card_on_name))
        } else if (TextUtils.isEmpty(cardNumber)) {
            validationOk = false
            showToast(getString(R.string.error_wrong_card_number))
        } else if (TextUtils.isEmpty(month)) {
            validationOk = false
            showToast(getString(R.string.error_wrong_card_month))
        } else if (TextUtils.isEmpty(year)) {
            validationOk = false
            showToast(getString(R.string.error_wrong_card_year))
        } else if (TextUtils.isEmpty(cvc)) {
            validationOk = false
            showToast(getString(R.string.error_wrong_card_cvc))
        }

        if (validationOk) {
            viewModel.onClickedPay(cardOnName, cardNumber, month, year, cvc)
        }
    }
}