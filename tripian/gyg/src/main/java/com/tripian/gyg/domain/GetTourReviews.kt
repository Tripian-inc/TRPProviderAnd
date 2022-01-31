package com.tripian.gyg.domain

import com.tripian.gyg.domain.model.ExperienceReview
import com.tripian.gyg.repository.model.toExperienceReview
import com.tripian.gyg.util.extensions.formatDate2String
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by semihozkoroglu on 13.08.2020.
 */
class GetTourReviews(val tourId: Long, val sortType: Int) : UseCase<List<ExperienceReview>>() {

    override fun execute(): Flow<List<ExperienceReview>> {
        return service.getTourReview(tourId, lang, currency).map {
            it.data?.reviews?.toExperienceReview()?.let {
                when (sortType) {
                    1 -> {
                        // Highest
                        it.sortedByDescending { it.rating ?: 0f }
                    }
                    2 -> {
                        // Lowest
                        it.sortedBy { it.rating ?: 0f }
                    }
                    else -> {
                        // 0 - Newest
                        it.sortedByDescending { it.date }
                    }
                }
            }?.onEach { it.date = formatDate2String(it.date, "yyyy-MM-dd", "dd MMM yyyy") } ?: arrayListOf()
        }
    }
}