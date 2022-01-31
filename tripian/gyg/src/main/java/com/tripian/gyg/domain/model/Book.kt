package com.tripian.gyg.domain.model

import com.tripian.gyg.repository.model.OptionCategory

/**
 * Created by semihozkoroglu on 30.08.2020.
 */
class BookCounter : BaseModel() {
    var category: String? = null
    var description: String? = null
    var count: Int = 0
    var minParticipant: Int = 0
    var maxParticipant: Int = 0
}

class BookOption : BaseModel() {
    var id: Long? = null
    var title: String? = null
    var day: String? = null
    var time: String? = null
    var count: Int = 0
    var price: Double = 0.0

    // hesaplama icin bunu kullaniyoruz
    var categories: List<OptionCategory>? = null
    var startTime: String? = null
}

class CountryCode : BaseModel() {
    var displayName: String? = null
    var code: String? = null
}