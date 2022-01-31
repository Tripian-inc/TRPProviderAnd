package com.tripian.gyg.domain

import com.tripian.gyg.domain.model.BookCounter
import com.tripian.gyg.domain.model.BookOption
import com.tripian.gyg.repository.model.Availability
import com.tripian.gyg.util.exception.ValidationException
import com.tripian.gyg.util.extensions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

/**
 * Created by semihozkoroglu on 13.08.2020.
 */
class GetAvailability(val tourId: Long, val startDate: Long) : UseCase<Pair<List<BookCounter>, List<BookOption>>>() {

    private var counters = arrayListOf<BookCounter>()
    private var options = arrayListOf<BookOption>()

    private var availabilities = arrayListOf<Availability>()

    override fun execute(): Flow<Pair<List<BookCounter>, List<BookOption>>> {
        return service.getAvailabilities(tourId, lang, currency, startDate.getRequestStartDate(), startDate.getRequestEndDate()).map {
            val date = getDate(startDate)
            var validationOk = false

            it.data?.availabilities?.forEach {
                if (it.startTime != null && it.startTime.contains(date)) {
                    availabilities.add(it)
                    validationOk = true
                }
            }

            if (!validationOk) {
                val availableDate = it.data?.availabilities?.get(0)

                throw ValidationException(
                    "Sorry, there is no availability for the selected date. ${
                        if (availableDate?.startTime != null) {
                            "Try " + availableDate.startTime.formatDate()
                        } else {
                            ""
                        }
                    }"
                )
            }

            coroutineScope {
                service.getTourOptions(tourId, lang, currency).collect {
                    it.data?.options?.forEach { op ->
                        async(Dispatchers.IO) {
                            val option = BookOption()
                            option.id = op.optionId
                            option.title = "${op.duration}-${op.durationUnit} ${op.title}"
                            option.day = "${op.duration} ${op.durationUnit}"

                            service.getOptionDetail(op.optionId!!, lang, currency).collect {
                                it.data?.pricing?.forEach { pricing ->
                                    val start = availabilities.find { it.pricingId == pricing.pricingId }

                                    if (start != null) {
                                        var isDefaultAdded = false

                                        pricing.categories?.forEach {
                                            val counter = BookCounter()
                                            counter.category = it.name
                                            counter.description = "Age ${it.minAge}-${it.maxAge}"
                                            counter.minParticipant = it.scale?.get(0)?.minParticipants ?: 0
                                            counter.maxParticipant = it.scale?.get(0)?.maxParticipants ?: 0

                                            if (!isDefaultAdded) {
                                                isDefaultAdded = true

                                                counter.count = 1

                                                option.count = 1
                                                option.price = it.scale?.get(0)?.retailPrice ?: 0.0
                                            }

                                            if (!counters.any { it.category == counter.category }) {
                                                counters.add(counter)
                                            }
                                        }

                                        option.time = start.startTime?.formatTime()

                                        option.startTime = start.startTime
                                        option.categories = pricing.categories
                                        options.add(option)

                                        return@collect
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Pair(counters, options)
        }
    }
}