package com.tripian.gyg.domain

import com.tripian.gyg.repository.model.PaymentConfigurationResponse
import com.tripian.gyg.util.exception.ValidationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by semihozkoroglu on 13.08.2020.
 */
class GetConfiguration(val country: String) : UseCase<PaymentConfigurationResponse>() {

    override fun execute(): Flow<PaymentConfigurationResponse> {
        return service.getConfiguration(lang, country, currency).map {
            val method = it.data?.methods?.find { it.name == "encrypted_credit_card" } ?: throw ValidationException("Cannot find encrypted_credit_card method!")

            if (method.publicKey == null) {
                throw ValidationException("Cannot find encrypted_credit_card Public Key not found!")
            }

            it
        }
    }
}