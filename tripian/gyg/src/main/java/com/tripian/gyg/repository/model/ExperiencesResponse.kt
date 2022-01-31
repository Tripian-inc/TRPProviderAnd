package com.tripian.gyg.repository.model

import com.google.gson.annotations.SerializedName
import com.tripian.gyg.domain.model.*
import java.util.*

/**
 * Created by semihozkoroglu on 31.12.2021.
 */
class ExperiencesResponse : BaseModel() {
    val data: ExperiencesData? = null
}

open class ExperiencesData : BaseModel() {
    val tours: List<TourData>? = null
}

open class TourData : BaseModel() {
    @SerializedName("tour_id")
    val tourId: Long? = null

    @SerializedName("tour_code")
    val tourCode: String? = null
    val title: String? = null
    val abstract: String? = null
    val description: String? = null
    val bestseller: Boolean? = null
    val certified: Boolean? = null
    val inclusions: String? = null
    val exclusions: String? = null

    @SerializedName("cancellation_policy_text")
    val cancellation: String? = null

    @SerializedName("cond_language")
    val languages: List<String>? = null

    val highlights: List<String>? = null

    @SerializedName("has_pick_up")
    val hasPickUp: Boolean? = null

    @SerializedName("overall_rating")
    val rating: Float? = null

    @SerializedName("number_of_ratings")
    val ratingCount: Float? = null

    val pictures: List<TourImageData>? = null
    val coordinates: TourCoordinateData? = null
    val price: TourPriceData? = null
    val categories: List<TourCategoryData>? = null
    val locations: List<TourLocationInfoData>? = null
    val url: String? = null
    val durations: List<TourDurationData>? = null
}

fun TourData.toExperiencesItem(): ExperiencesItem {
    return ExperiencesItem().apply {
        id = this@toExperiencesItem.tourId
        title = this@toExperiencesItem.title
        rating = this@toExperiencesItem.rating
        rateCount = this@toExperiencesItem.ratingCount ?: 0f
        price = this@toExperiencesItem.price?.values?.amount
        image = pictures?.getOrNull(0)?.url
        bestSeller = this@toExperiencesItem.bestseller ?: false
    }
}

fun TourData.toExperienceDetail(): ExperienceDetail {
    return ExperienceDetail().apply {
        id = this@toExperienceDetail.tourId
        title = this@toExperienceDetail.title
        abstract = this@toExperienceDetail.abstract
        description = this@toExperienceDetail.description
        rating = this@toExperienceDetail.rating
        rateCount = this@toExperienceDetail.ratingCount ?: 0f
        price = this@toExperienceDetail.price?.values?.amount
        images = pictures?.map { it.url ?: "" }
        duration = this@toExperienceDetail.durations?.joinToString { "${it.duration} ${it.unit}" }
        liveGuide = this@toExperienceDetail.languages?.joinToString { Locale(it).displayName }
        cancellation = this@toExperienceDetail.cancellation
        highlights = this@toExperienceDetail.highlights
        include = this@toExperienceDetail.inclusions
        exclude = this@toExperienceDetail.exclusions
        url = this@toExperienceDetail.url
    }
}

fun TourReviewObj.toExperienceReview(): List<ExperienceReview>? {
    return this.items?.map {
        ExperienceReview().apply {
            date = it.date
            rating = it.rating
            reviewer = it.reviewer
            comment = it.comment
        }
    }
}

fun TourData.isCategoryOk(category: ExperienceCategory): Boolean {
    return categories?.find { it.categoryId == category.id } != null
}

fun TourData.isDurationOk(): Boolean {
    val time = durations?.find { it.unit == "day" }

    return if (time?.duration == null) {
        true
    } else {
        time.duration > 1.0f
    }
}

open class TourImageData : BaseModel() {
    val id: String? = null
    val url: String? = null
        get() {
            return field?.replace("[format_id]", "21")
        }
}

open class TourCoordinateData : BaseModel() {
    val lat: Float? = null
    val long: Float? = null
}

open class TourPriceData : BaseModel() {
    val description: String? = null
    val values: TourPriceValueData? = null
}

open class TourPriceValueData : BaseModel() {
    val amount: String? = null
}

open class TourCategoryData : BaseModel() {
    @SerializedName("category_id")
    val categoryId: Long? = null
    val name: String? = null
}

open class TourLocationInfoData : BaseModel() {
    @SerializedName("location_id")
    val locationId: Long? = null
    val type: String? = null
    val name: String? = null

    @SerializedName("english_name")
    val englishName: String? = null
    val country: String? = null
    val coordinates: TourCoordinateData? = null

    @SerializedName("parent_id")
    val parentId: Long? = null
}

open class TourDurationData : BaseModel() {
    val duration: Float? = null
    val unit: String? = null
}