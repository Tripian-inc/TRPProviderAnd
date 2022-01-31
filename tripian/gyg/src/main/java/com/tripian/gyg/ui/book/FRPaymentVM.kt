package com.tripian.gyg.ui.book

import adyen.com.adyencse.pojo.Card
import androidx.lifecycle.MutableLiveData
import com.tripian.gyg.base.BaseViewModel
import com.tripian.gyg.domain.GetConfiguration
import com.tripian.gyg.domain.Payment
import com.tripian.gyg.repository.model.PaymentBrand
import com.tripian.gyg.util.exception.ValidationException
import com.tripian.gyg.util.extensions.launch
import dialog
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import java.util.*

class FRPaymentVM : BaseViewModel() {

    var showImagesListener = MutableLiveData<List<PaymentBrand>>()

    fun init() = launch {
        GetConfiguration(BookingPageData.data.billing?.countryCode ?: "").execute()
            .onStart { loading { true } }
            .onCompletion { loading { false } }
            .catch {
                if (it is ValidationException) {
                    goBack()
                }

                throw it
            }
            .collect {
                val method = it.data?.methods?.find { it.name == "encrypted_credit_card" }

                if (method != null) {
                    BookingPageData.data.publicKey = method.publicKey ?: ""

                    showImagesListener.postValue(method.brands)
                }
            }
    }

    fun onClickedPay(cardOnName: String, cardNumber: String, month: String, year: String, cvc: String) = launch {
        val card: Card = Card.Builder()
            .setHolderName(cardOnName.trim())
            .setCvc(cvc.trim())
            .setExpiryMonth(month)
            .setExpiryYear(year)
            .setGenerationTime(Calendar.getInstance().time)
            .setNumber(cardNumber.replace(" ", "").trim())
            .build()

        val encryptedCard: String = card.serialize(BookingPageData.data.publicKey)

        Payment(BookingPageData.data.billing!!, BookingPageData.data.traveler!!, encryptedCard, BookingPageData.data.bookingRes!!.shoppingCartId!!)
            .execute()
            .onStart { loading { true } }
            .onCompletion { loading { false } }
            .catch {
                if (it is ValidationException) {
                    goBack()
                }

                throw it
            }
            .collect {
                dialog {
                    titleText = "Success"
                    contentText = "Payment completed successfully"
                }
            }
    }
}
