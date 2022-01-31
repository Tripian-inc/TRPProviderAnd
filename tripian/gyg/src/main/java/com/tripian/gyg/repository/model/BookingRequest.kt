package com.tripian.gyg.repository.model

import com.google.gson.annotations.SerializedName
import com.tripian.gyg.domain.model.BaseModel

/**
 * Created by semihozkoroglu on 31.12.2021.
 */
class BookingRequest : BaseModel() {
    @SerializedName("base_data")
    var base: BaseData? = null
    var data: BookingData? = null
}

class BaseData : BaseModel() {
    var currency: String? = null

    @SerializedName("cnt_language")
    var lang: String? = null
}

class BookingData : BaseModel() {
    var booking: BookingModel? = null
}

class BookingModel : BaseModel() {
    var bookable: Bookable? = null
}

class Bookable : BaseModel() {
    @SerializedName("booking_parameters")
    var parameters: List<BookingParameters>? = null
    var price: Double = 0.0

    @SerializedName("option_id")
    var optionId: Long? = null

    @Transient
    var optionTitle: String? = null

    var datetime: String? = null
    var categories: List<BookingCategory>? = null
}

class BookingParameters : BaseModel() {
    var name: String? = null

    @SerializedName("value_1")
    var value1: String? = null

    @SerializedName("value_2")
    var value2: String? = null
}

class BookingCategory : BaseModel() {
    @SerializedName("number_of_participants")
    var participants: Int? = null

    @SerializedName("category_id")
    var categoryId: Long? = null

    @Transient
    var categoryName: String? = null
}