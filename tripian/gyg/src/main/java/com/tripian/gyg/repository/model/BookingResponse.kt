package com.tripian.gyg.repository.model

import com.google.gson.annotations.SerializedName
import com.tripian.gyg.domain.model.BaseModel

/**
 * Created by semihozkoroglu on 31.12.2021.
 */

// ---- Bookings

class BookingResponse : BaseModel() {
    val data: BookingResData? = null
}

open class BookingResData : BaseModel() {
    val bookings: BookingRes? = null
}

open class BookingRes : BaseModel() {
    @SerializedName("shopping_cart_id")
    val shoppingCartId: Long? = null

    @SerializedName("shopping_cart_hash")
    val shoppingCartHash: String? = null

    @SerializedName("booking_id")
    val bookingId: Long? = null

    @SerializedName("booking_hash")
    val bookingHash: String? = null
}

// ---- Get Carts

class CartsResponse : BaseModel() {
    val data: CartsData? = null
}

open class CartsData : BaseModel() {
    @SerializedName("shopping_cart")
    val shoppingCart: ShoppingCart? = null
}

open class ShoppingCart : BaseModel() {
    @SerializedName("shopping_cart_id")
    val shoppingCartId: String? = null

    @SerializedName("shopping_cart_hash")
    val shoppingCartHash: String? = null

    val bookings: List<ShoppingBook>? = null
}

open class ShoppingBook : BaseModel() {
    @SerializedName("booking_id")
    val bookingId: Long? = null

    @SerializedName("booking_hash")
    val bookingHash: String? = null
}