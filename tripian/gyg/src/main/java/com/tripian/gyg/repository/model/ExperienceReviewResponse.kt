package com.tripian.gyg.repository.model

import com.google.gson.annotations.SerializedName
import com.tripian.gyg.domain.model.BaseModel

/**
 * Created by semihozkoroglu on 31.12.2021.
 */
class ExperienceReviewResponse : BaseModel() {
    var data: TourReviewData? = null
}

open class TourReviewData : BaseModel() {
    val reviews: TourReviewObj? = null
}

open class TourReviewObj : BaseModel() {
    @SerializedName("review_items")
    val items: List<TourReview>? = null
}

open class TourReview : BaseModel() {
    @SerializedName("review_date")
    val date: String? = null
    val comment: String? = null

    @SerializedName("reviewer_name")
    val reviewer: String? = null

    @SerializedName("review_rating")
    val rating: Float? = null

    @SerializedName("review_rating_value")
    val ratingCount: Float? = null
}