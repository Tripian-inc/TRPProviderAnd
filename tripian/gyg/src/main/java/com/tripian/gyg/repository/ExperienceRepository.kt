package com.tripian.gyg.repository

import com.tripian.gyg.domain.model.BaseModel
import com.tripian.gyg.repository.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

object ExperienceRepository {

    private val service: ExperiencesService = ExperienceNetwork().getService()

    var tourReview: TourReviewData? = null
    var cachedTourId: Long? = null

    fun getTours(language: String, currency: String, q: String, limit: Int): Flow<ExperiencesResponse> = sendRequest {
        service.getTours(language, currency, q, limit)
    }

    fun getTourDetail(tourId: Long, language: String, currency: String): Flow<ExperiencesResponse> = sendRequest {
        service.getTourDetail(tourId, language, currency)
    }

    fun getAvailabilities(tourId: Long, language: String, currency: String, dateFrom: String, dateTo: String): Flow<AvailabilityResponse> = sendRequest {
        service.getAvailabilities(tourId, language, currency, dateFrom, dateTo)
    }

    fun getTourOptions(tourId: Long, language: String, currency: String): Flow<TourOptionResponse> = sendRequest {
        service.getTourOptions(tourId, language, currency)
    }

    fun getOptionDetail(optionId: Long, language: String, currency: String): Flow<OptionDetailResponse> = sendRequest {
        service.getOptionDetail(optionId, language, currency)
    }

    fun getTourReview(tourId: Long, language: String, currency: String): Flow<ExperienceReviewResponse> {
        return if (tourReview != null && cachedTourId == tourId) {
            flow {
                emit(ExperienceReviewResponse().apply {
                    data = tourReview
                })
            }
        } else {
            sendRequest {
                service.getTourReviews(tourId, language, currency)
            }.map {
                cachedTourId = tourId
                tourReview = it.data
                it
            }
        }
    }

    fun booking(request: BookingRequest): Flow<BookingResponse> = sendRequest {
        service.bookings(request)
    }

    fun getCarts(shoppingCartHash: String, language: String, currency: String): Flow<CartsResponse> = sendRequest {
        service.getCarts(shoppingCartHash, language, currency)
    }

    fun deleteBooking(shoppingCartHash: String): Flow<BaseModel> = sendRequest {
        service.deleteBooking(shoppingCartHash)
    }

    fun getConfiguration(language: String, country: String, currency: String): Flow<PaymentConfigurationResponse> = sendRequest {
        service.getConfiguration(language, country, currency)
    }

    fun pay(request: PaymentRequest): Flow<BaseModel> = sendRequest {
        service.pay(request)
    }

    fun <T> sendRequest(req: suspend () -> T): Flow<T> {
        return flow { emit(req()) }
    }
}