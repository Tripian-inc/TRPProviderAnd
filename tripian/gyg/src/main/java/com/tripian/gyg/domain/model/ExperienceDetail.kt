package com.tripian.gyg.domain.model

/**
 * Created by semihozkoroglu on 30.08.2020.
 */
class ExperienceDetail : BaseModel() {
    var id: Long? = null
    var title: String? = null
    var abstract: String? = null
    var description: String? = null
    var rating: Float? = null
    var rateCount: Float = 0f
    var price: String? = null
    var images: List<String>? = null
    var duration: String? = null
    var liveGuide: String? = null
    var cancellation: String? = null
    var highlights: List<String>? = null
    var include: String? = null
    var exclude: String? = null
    var url: String? = null
    var reviews: List<ExperienceReview>? = null
}