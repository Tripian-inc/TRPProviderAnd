package com.tripian.gyg.domain

import com.tripian.gyg.domain.model.ExperienceDetail
import com.tripian.gyg.repository.model.toExperienceDetail
import com.tripian.gyg.repository.model.toExperienceReview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

/**
 * Created by semihozkoroglu on 13.08.2020.
 */
class GetTourDetail(val tourId: Long) : UseCase<ExperienceDetail>() {

    override fun execute(): Flow<ExperienceDetail> {
        return service.getTourDetail(tourId, lang, currency).zip(
            service.getTourReview(tourId, lang, currency)
        ) { t1, t2 ->
            t1.data?.tours?.find { it.tourId == tourId }?.toExperienceDetail()?.apply {
                reviews = t2.data?.reviews?.toExperienceReview()
            } ?: ExperienceDetail()
        }
    }
}