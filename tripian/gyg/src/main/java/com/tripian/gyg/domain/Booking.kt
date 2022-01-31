package com.tripian.gyg.domain

import com.tripian.gyg.repository.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by semihozkoroglu on 13.08.2020.
 */
class Booking(val bookable: Bookable) : UseCase<BookingResponse>() {

    override fun execute(): Flow<BookingResponse> {
        return service.booking(BookingRequest().apply {
            base = BaseData().apply {
                currency = this@Booking.currency
                lang = this@Booking.lang
            }
            data = BookingData().apply {
                booking = BookingModel().apply {
                    bookable = this@Booking.bookable.apply {
                        parameters = arrayListOf<BookingParameters>().apply {
                            add(BookingParameters().apply {
                                name = "language"
                                value1 = "language_booklet"
                                value2 = lang
                            })
                            add(BookingParameters().apply {
                                name = "hotel"
                                value1 = "Hotel"
                            })
                        }
                    }
                }
            }
        })
    }
}