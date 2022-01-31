package com.tripian.gyg.domain

import com.tripian.gyg.domain.model.ExperienceCategory
import com.tripian.gyg.domain.model.Experiences
import com.tripian.gyg.repository.model.isCategoryOk
import com.tripian.gyg.repository.model.isDurationOk
import com.tripian.gyg.repository.model.toExperiencesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by semihozkoroglu on 13.08.2020.
 */
class GetTours : UseCase<List<Experiences>>() {

    private val itemLimit = 20
    override fun execute(): Flow<List<Experiences>> {
        return service.getTours(
            lang, currency,
            "istanbul",
            limit = 90
        ).map {
            val adventure = Experiences().apply {
                title = ExperienceCategory.ADVENTURE.names
            }
            val food = Experiences().apply {
                title = ExperienceCategory.FOOD.names
            }
            val cultureAndHistory = Experiences().apply {
                title = ExperienceCategory.CULTURE_AND_HISTORY.names
            }
            val sightseeing = Experiences().apply {
                title = ExperienceCategory.SIGHT_SEEING.names
            }
            val artAndMuseums = Experiences().apply {
                title = ExperienceCategory.ART_AND_MUSEUMS.names
            }
            val neighbourhood = Experiences().apply {
                title = ExperienceCategory.LOCAL_AND_NEIGBORHOOD.names
            }

            it.data?.tours?.forEach { tour ->
                if (tour.isDurationOk()) {
                    when {
                        adventure.items.size < itemLimit && tour.isCategoryOk(ExperienceCategory.ADVENTURE) -> {
                            adventure.items.add(tour.toExperiencesItem())
                        }
                        food.items.size < itemLimit && tour.isCategoryOk(ExperienceCategory.FOOD) -> {
                            food.items.add(tour.toExperiencesItem())
                        }
                        cultureAndHistory.items.size < itemLimit && tour.isCategoryOk(ExperienceCategory.CULTURE_AND_HISTORY) -> {
                            cultureAndHistory.items.add(tour.toExperiencesItem())
                        }
                        sightseeing.items.size < itemLimit && tour.isCategoryOk(ExperienceCategory.SIGHT_SEEING) -> {
                            sightseeing.items.add(tour.toExperiencesItem())
                        }
                        artAndMuseums.items.size < itemLimit && tour.isCategoryOk(ExperienceCategory.ART_AND_MUSEUMS) -> {
                            artAndMuseums.items.add(tour.toExperiencesItem())
                        }
                        neighbourhood.items.size < itemLimit && tour.isCategoryOk(ExperienceCategory.LOCAL_AND_NEIGBORHOOD) -> {
                            neighbourhood.items.add(tour.toExperiencesItem())
                        }
                    }
                }
            }

            arrayListOf<Experiences>().apply {
                if (!adventure.items.isNullOrEmpty()) add(adventure)
                if (!food.items.isNullOrEmpty()) add(food)
                if (!cultureAndHistory.items.isNullOrEmpty()) add(cultureAndHistory)
                if (!sightseeing.items.isNullOrEmpty()) add(sightseeing)
                if (!artAndMuseums.items.isNullOrEmpty()) add(artAndMuseums)
                if (!neighbourhood.items.isNullOrEmpty()) add(neighbourhood)
            }
        }
    }
}