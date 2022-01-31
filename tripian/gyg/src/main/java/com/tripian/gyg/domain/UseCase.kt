package com.tripian.gyg.domain

import com.tripian.gyg.repository.ExperienceRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by semihozkoroglu on 28.01.2022.
 */
abstract class UseCase<R> {

    val lang = "en"
    val currency = "usd"

    val service = ExperienceRepository

    abstract fun execute(): Flow<R>
}