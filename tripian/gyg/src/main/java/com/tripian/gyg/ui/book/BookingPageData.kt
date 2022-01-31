package com.tripian.gyg.ui.book

import com.tripian.gyg.repository.model.Billing
import com.tripian.gyg.repository.model.Bookable
import com.tripian.gyg.repository.model.BookingRes
import com.tripian.gyg.repository.model.Traveler

/**
 * Created by semihozkoroglu on 31.01.2022.
 */
class BookingPageData {
    var title: String = ""
    var tourId: Long = 0
    var bookable: Bookable? = null
    var bookingRes: BookingRes? = null
    var billing: Billing? = null
    var traveler: Traveler? = null
    var publicKey: String = ""

    companion object {
        private var _obj: BookingPageData? = null

        val data: BookingPageData
            get() {
                if (_obj == null)
                    _obj = BookingPageData()

                return _obj!!
            }

        fun clearBooking() {
            _obj?.bookable = null
            _obj?.bookingRes = null
        }

        fun clearTraveler() {
            _obj?.billing = null
            _obj?.traveler = null
        }

        fun destroy() {
            _obj = null
        }
    }
}