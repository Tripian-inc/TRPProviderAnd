package com.tripian.gyg.domain

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by semihozkoroglu on 13.08.2020.
 */
class CheckBooking(val currentBookingId: Long, val shoppingCartHash: String) : UseCase<Unit>() {

    override fun execute(): Flow<Unit> {
        return service.getCarts(shoppingCartHash, lang, currency).map {
            coroutineScope {
                Log.e("okhttp", "currentBookingId: $currentBookingId")

                it.data?.shoppingCart?.bookings?.forEach {
                    if (it.bookingId != currentBookingId && it.bookingHash != null) {
                        Log.e("okhttp", "deleted BookingId: ${it.bookingId} ${it.bookingHash}")

                        async(Dispatchers.IO) { service.deleteBooking(it.bookingHash) }
                    } else {
                        Log.e("okhttp", "nothing BookingId: ${it.bookingId}")
                    }
                }
            }
        }
    }
}