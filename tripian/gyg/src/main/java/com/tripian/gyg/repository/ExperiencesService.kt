package com.tripian.gyg.repository

import com.tripian.gyg.domain.model.BaseModel
import com.tripian.gyg.repository.model.*
import retrofit2.http.*

interface ExperiencesService {

    @GET("tours")
    suspend fun getTours(
        @Query("cnt_language") language: String,
        @Query("currency") currency: String,
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("preformatted") preformatted: String = "full"
    ): ExperiencesResponse

    @GET("tours/{tourId}")
    suspend fun getTourDetail(
        @Path("tourId") tourId: Long,
        @Query("cnt_language") language: String,
        @Query("currency") currency: String,
        @Query("preformatted") preformatted: String = "full"
    ): ExperiencesResponse

    @GET("reviews/tour/{tourId}")
    suspend fun getTourReviews(
        @Path("tourId") tourId: Long,
        @Query("cnt_language") language: String,
        @Query("currency") currency: String
    ): ExperienceReviewResponse

    @GET("tours/{tourId}/availabilities")
    suspend fun getAvailabilities(
        @Path("tourId") tourId: Long,
        @Query("cnt_language") language: String,
        @Query("currency") currency: String,
        @Query("date[]=") dateFrom: String,
        @Query("date[]=") dateTo: String,
    ): AvailabilityResponse

    @GET("tours/{tourId}/options")
    suspend fun getTourOptions(
        @Path("tourId") tourId: Long,
        @Query("cnt_language") language: String,
        @Query("currency") currency: String
    ): TourOptionResponse

    @GET("options/{optionId}/pricings")
    suspend fun getOptionDetail(
        @Path("optionId") optionId: Long,
        @Query("cnt_language") language: String,
        @Query("currency") currency: String
    ): OptionDetailResponse

    @POST("bookings")
    suspend fun bookings(@Body request: BookingRequest): BookingResponse

    @GET("carts/{shoppingCartHash}")
    suspend fun getCarts(
        @Path("shoppingCartHash") shoppingCartHash: String,
        @Query("cnt_language") language: String,
        @Query("currency") currency: String,
    ): CartsResponse

    @DELETE("bookings/{bookingHash}")
    suspend fun deleteBooking(@Path("bookingHash") bookingHash: String): BaseModel

    @GET("configuration/payment")
    suspend fun getConfiguration(
        @Query("cnt_language") language: String,
        @Query("country") country: String,
        @Query("currency") currency: String
    ): PaymentConfigurationResponse

    @POST("carts")
    suspend fun pay(@Body request: PaymentRequest): BaseModel
}