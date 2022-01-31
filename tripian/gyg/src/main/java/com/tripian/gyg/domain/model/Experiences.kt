package com.tripian.gyg.domain.model

/**
 * Created by semihozkoroglu on 30.08.2020.
 */
class Experiences : BaseModel() {
    var title: String? = null
    var items = ArrayList<ExperiencesItem>()
}