package com.tripian.gyg.repository.model

import com.google.gson.annotations.SerializedName
import com.tripian.gyg.domain.model.BaseModel

/**
 * Created by semihozkoroglu on 31.01.2022.
 */
class PaymentConfigurationResponse : BaseModel() {
    val data: PaymentConfigurationData? = null
}

open class PaymentConfigurationData : BaseModel() {
    @SerializedName("payment_methods")
    val methods: List<PaymentMethod>? = null
}

open class PaymentMethod : BaseModel() {
    val name: String? = null

    @SerializedName("public_key")
    val publicKey: String? = null
    val brands: List<PaymentBrand>? = null
}

open class PaymentBrand : BaseModel() {
    val code: Int? = null
    val name: String? = null
}