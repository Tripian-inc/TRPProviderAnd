package com.tripian.gyg.example

import android.app.Application
import com.tripian.gyg.base.Tripian

/**
 * Created by semihozkoroglu on 31.01.2022.
 */
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Tripian.init(this, "Yb8XauGtHKbBUEj4PGWRfrvzvmwKijdghHwWkjBVYTKmTFeR")
    }
}