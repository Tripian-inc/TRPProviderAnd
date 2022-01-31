package com.tripian.gyg.domain

import com.tripian.gyg.domain.model.BaseModel
import com.tripian.gyg.repository.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by semihozkoroglu on 13.08.2020.
 */
class Payment(var billing: Billing, var traveler: Traveler, var encryptedCard: String, var shoppingCartId: Long) : UseCase<BaseModel>() {

    override fun execute(): Flow<BaseModel> {
        return service.pay(PaymentRequest().apply {
            base = BaseData().apply {
                currency = this@Payment.currency
                lang = this@Payment.lang
            }
            data = PaymentData().apply {
                shoppingCart = PayShopping().apply {
                    billing = this@Payment.billing
                    traveler = this@Payment.traveler
                    payment = PaymentModel().apply {
                        this.card = PaymentCard().apply {
                            data = encryptedCard
                        }
                    }
                    shoppingCartId = this@Payment.shoppingCartId
                }
            }
        })
    }
}