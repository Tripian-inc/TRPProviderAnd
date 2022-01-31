package com.tripian.gyg.domain.model

/**
 * Created by semihozkoroglu on 30.08.2020.
 */
class ExperiencesItem : BaseModel() {
    var id: Long? = null
    var title: String? = null
    var rating: Float? = null
    var bestSeller: Boolean = false
    var rateCount: Float = 0f
    var price: String? = null
    var image: String? = null
}