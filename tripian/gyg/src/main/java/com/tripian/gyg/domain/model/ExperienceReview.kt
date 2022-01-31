package com.tripian.gyg.domain.model

/**
 * Created by semihozkoroglu on 30.08.2020.
 */
class ExperienceReview : BaseModel() {
    var date: String? = null
    var rating: Float? = null
    var reviewer: String? = null
    var comment: String? = null
}