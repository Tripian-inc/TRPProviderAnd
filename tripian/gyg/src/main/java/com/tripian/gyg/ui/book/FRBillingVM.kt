package com.tripian.gyg.ui.book

import com.tripian.gyg.base.BaseViewModel
import com.tripian.gyg.domain.CheckBooking
import com.tripian.gyg.repository.model.Billing
import com.tripian.gyg.repository.model.Traveler
import com.tripian.gyg.util.extensions.launch
import com.tripian.gyg.util.extensions.navigateToFragment
import com.tripian.gyg.util.fragment.AnimationType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class FRBillingVM : BaseViewModel() {

    fun init() = launch {
        if (BookingPageData.data.bookingRes?.bookingId != null && BookingPageData.data.bookingRes?.shoppingCartHash != null) {
            CheckBooking(BookingPageData.data.bookingRes!!.bookingId!!, BookingPageData.data.bookingRes!!.shoppingCartHash!!)
                .execute()
                .onStart { loading { true } }
                .onCompletion { loading { false } }
                .collect { }
        }
    }

    fun send(
        firstName: String, lastName: String, email: String, phone: String, country: String, useBillingInfo: Boolean,
        travelFirstName: String, travelLastName: String, travelEmail: String, travelPhone: String
    ) {
        BookingPageData.data.billing = Billing().apply {
            this.firstName = firstName
            this.lastName = lastName
            this.phoneNumber = phone
            this.email = email
            this.countryCode = country
        }

        BookingPageData.data.traveler = if (useBillingInfo) {
            Traveler().apply {
                this.firstName = firstName
                this.lastName = lastName
                this.phoneNumber = phone
                this.email = email
            }
        } else {
            Traveler().apply {
                this.firstName = travelFirstName
                this.lastName = travelLastName
                this.phoneNumber = travelEmail
                this.email = travelPhone
            }
        }

        navigateToFragment(FRPayment.newInstance(), animation = AnimationType.ENTER_FROM_RIGHT)
    }

    fun onClickedCountry() {
        navigateToFragment(FRCountrySelect.newInstance())
    }
}
