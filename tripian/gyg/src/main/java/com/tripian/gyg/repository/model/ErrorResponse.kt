package com.tripian.gyg.repository.model

import com.tripian.gyg.domain.model.BaseModel

/**
 * Created by semihozkoroglu on 31.01.2022.
 */
class ErrorResponse : BaseModel() {
    var errors: List<ErrorModel>? = null
}

class ErrorModel : BaseModel() {
    var errorCode: Int? = null
    var errorMessage: String? = null
}