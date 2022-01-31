package com.tripian.gyg.ui.book

import androidx.lifecycle.MutableLiveData
import com.tripian.gyg.base.BaseViewModel
import com.tripian.gyg.domain.Booking
import com.tripian.gyg.domain.GetAvailability
import com.tripian.gyg.domain.model.BookCounter
import com.tripian.gyg.domain.model.BookOption
import com.tripian.gyg.domain.model.Experiences
import com.tripian.gyg.repository.model.Bookable
import com.tripian.gyg.repository.model.BookingCategory
import com.tripian.gyg.util.extensions.*
import com.tripian.gyg.util.fragment.AnimationType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class FRTourDateVM : BaseViewModel() {

    var onTourListener = MutableLiveData<List<Experiences>>()
    var onSetDateListener = MutableLiveData<String>()
    var onSetCounterListener = MutableLiveData<List<BookCounter>?>()
    var onSetOptionsListener = MutableLiveData<Pair<List<BookOption>?, Boolean>>()

    val calendar = Calendar.getInstance()

    private lateinit var counters: List<BookCounter>
    private lateinit var options: List<BookOption>

    fun init() {
        if (!::options.isInitialized) {
            onDateSelected(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        }
    }

    private fun getList() = launch {
        GetAvailability(BookingPageData.data.tourId, calendar.timeInMillis).execute()
            .onStart { loading { true } }
            .onCompletion { loading { false } }
            .collect {
                counters = it.first
                options = it.second

                onSetCounterListener.postValue(it.first)
                onSetOptionsListener.postValue(Pair(it.second, true))
            }
    }

    fun onDateSelected(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        onSetDateListener.postValue("$dayOfMonth ${getMonthName(monthOfYear)} $year")

        getList()
    }

    fun getYear(): Int {
        return calendar.timeInMillis.getYear().toInt()
    }

    fun getMonth(): Int {
        return calendar.timeInMillis.getMonth().toInt() - 1
    }

    fun getDay(): Int {
        return calendar.timeInMillis.getDay().toInt()
    }

    fun onChangedCounter() {
        options.forEach { option ->
            option.count = 0
            option.price = 0.0

            counters.forEach { counter ->
                if (counter.count > 0) {
                    option.count += counter.count
                    option.categories?.find { it.name == counter.category }?.scale?.get(0)?.retailPrice?.let {
                        option.price = BigDecimal(option.price).plus(BigDecimal(it).multiply(BigDecimal(counter.count))).setScale(2, RoundingMode.HALF_UP).toDouble()
                    }
                }
            }
        }

        onSetOptionsListener.postValue(Pair(options, false))
    }

    fun onSelectedOption(option: BookOption) = launch {
        val items = arrayListOf<BookingCategory>()
        counters.forEach { counter ->
            if (counter.count > 0) {
                val category = option.categories?.find { it.name == counter.category }
                items.add(BookingCategory().apply {
                    participants = counter.count
                    categoryId = category?.id
                    categoryName = category?.name
                })
            }
        }

        val bookable = Bookable().apply {
            price = option.price
            optionId = option.id
            optionTitle = option.title
            datetime = option.startTime
            categories = items
        }

        BookingPageData.data.bookable = bookable

        Booking(bookable).execute()
            .onStart { loading { true } }
            .onCompletion { loading { false } }
            .collect {
                BookingPageData.data.bookingRes = it.data?.bookings

                navigateToFragment(FRBilling.newInstance(), animation = AnimationType.ENTER_FROM_RIGHT)
            }
    }
}
