package com.tripian.gyg.util.fragment

import com.tripian.gyg.R

/**
 * Created by Semih Özköroğlu on 29.09.2019
 */
enum class AnimationType {
    ENTER_FROM_LEFT,
    ENTER_FROM_RIGHT,
    NO_ANIM;

    companion object {
        fun getAnimation(type: AnimationType): List<Int> {
            when (type) {
                ENTER_FROM_LEFT -> return listOf(R.anim.anim_horizontal_fragment_in_from_pop, R.anim.anim_horizontal_fragment_out_from_pop, R.anim.anim_horizontal_fragment_in, R.anim.anim_horizontal_fragment_out)
                ENTER_FROM_RIGHT -> return listOf(R.anim.anim_horizontal_fragment_in, R.anim.anim_horizontal_fragment_out, R.anim.anim_horizontal_fragment_in_from_pop, R.anim.anim_horizontal_fragment_out_from_pop)
            }

            return listOf()
        }
    }
}