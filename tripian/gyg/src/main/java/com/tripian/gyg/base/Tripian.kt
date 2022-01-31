package com.tripian.gyg.base

import android.content.Context
import android.content.Intent
import com.tripian.gyg.ui.ACExperiences

/**
 * Created by semihozkoroglu on 31.01.2022.
 */
class Tripian {

    companion object {
        private var context: Context? = null
        private var gygApiKey: String = ""

        fun init(applicationContext: Context, apiKey: String) {
            context = applicationContext
            gygApiKey = apiKey
        }

        fun apiKey() = gygApiKey

        fun startExperience() {
            context?.startActivity(Intent(context, ACExperiences::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
}