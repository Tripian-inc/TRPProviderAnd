package com.tripian.gyg.ui.book

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.tripian.gyg.R
import com.tripian.gyg.base.BaseFragment
import com.tripian.gyg.databinding.FrBillingBinding
import com.tripian.gyg.domain.model.CountryCode
import com.tripian.gyg.util.datalistener.injectVM
import com.tripian.gyg.util.extensions.isValidEmail

/**
 * Created by semihozkoroglu on 28.01.2022.
 */
class FRBilling : BaseFragment<FrBillingBinding>() {

    override val binding: (LayoutInflater) -> FrBillingBinding = FrBillingBinding::inflate

    private val viewModel: FRBillingVM by injectVM()

    companion object {
        fun newInstance(): FRBilling {
            return FRBilling()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BookingPageData.clearTraveler()

        viewModel.init()
    }

    override fun onResume() {
        super.onResume()

        (requireActivity() as ACBook).setPageTitle(R.string.page_title_billing)
    }

    override fun setListeners() {
        vi.etCountry.setOnClickListener { viewModel.onClickedCountry() }
        vi.btnSwitch.setOnCheckedChangeListener { _, isChecked ->
            vi.llTraveler.isVisible = !isChecked
        }

        vi.btnNext.setOnClickListener {
            val code = if (vi.etCountry.tag != null) vi.etCountry.tag as String else ""

            send(
                vi.etFirstName.text.toString(), vi.etLastName.text.toString(), vi.etMail.text.toString(), vi.etPhone.text.toString(), code, vi.btnSwitch.isChecked,
                vi.etTravelFirstName.text.toString(), vi.etTravelLastName.text.toString(), vi.etTravelMail.text.toString(), vi.etTravelPhone.text.toString()
            )
        }

        setFragmentResultListener("CountryCode") { requestKey, result ->
            (result.getSerializable("country") as CountryCode).let {
                vi.etCountry.tag = it.code
                vi.etCountry.setText(it.displayName)
            }
        }
    }

    override fun setReceivers() {
    }

    private fun send(
        firstName: String, lastName: String, email: String, phone: String, country: String, useBillingInfo: Boolean,
        travelFirstName: String, travelLastName: String, travelEmail: String, travelPhone: String
    ) {
        var validationOk = true

        if (TextUtils.isEmpty(firstName)) {
            validationOk = false
            showToast(getString(R.string.error_wrong_first_name))
        } else if (TextUtils.isEmpty(lastName)) {
            validationOk = false
            showToast(getString(R.string.error_wrong_last_name))
        } else if (!isValidEmail(email)) {
            validationOk = false
            showToast(getString(R.string.error_wrong_mail))
        } else if (TextUtils.isEmpty(phone)) {
            validationOk = false
            showToast(getString(R.string.error_wrong_phone))
        } else if (TextUtils.isEmpty(country)) {
            validationOk = false
            showToast(getString(R.string.error_wrong_country))
        } else if (!useBillingInfo) {
            if (TextUtils.isEmpty(travelFirstName)) {
                validationOk = false
                showToast(getString(R.string.error_wrong_travel_first_name))
            } else if (TextUtils.isEmpty(travelLastName)) {
                validationOk = false
                showToast(getString(R.string.error_wrong_travel_last_name))
            } else if (!isValidEmail(travelEmail)) {
                validationOk = false
                showToast(getString(R.string.error_wrong_travel_mail))
            } else if (TextUtils.isEmpty(travelPhone)) {
                validationOk = false
                showToast(getString(R.string.error_wrong_travel_phone))
            }
        }

        if (validationOk) {
            viewModel.send(firstName, lastName, email, phone, country, useBillingInfo, travelFirstName, travelLastName, travelEmail, travelPhone)
        }
    }
}