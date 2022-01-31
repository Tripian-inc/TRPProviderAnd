package com.tripian.gyg.util.extensions

import android.annotation.SuppressLint
import android.text.TextUtils
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by semihozkoroglu on 19.08.2020.
 */
fun formatDate2String(
    dateText: String?,
    inputFormat: String = "yyyy-MM-dd hh:mm:ss",
    outputformat: String = "yyyy-MM-dd"
): String {
    var sdf: SimpleDateFormat?
    val date: Date?

    if (dateText != null && dateText.isNotEmpty()) {
        try {
            sdf = SimpleDateFormat(inputFormat)

            date = sdf.parse(dateText)

            sdf = SimpleDateFormat(outputformat, Locale.ENGLISH)

            return sdf.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return ""
}

@SuppressLint("SimpleDateFormat")
fun formatDate2Timestamp(dateText: String?, inputFormat: String = "yyyy-MM-dd hh:mm:ss"): Long {
    val sdf: SimpleDateFormat?
    val date: Date?

    if (dateText != null && dateText.isNotEmpty()) {
        try {
            sdf = SimpleDateFormat(inputFormat)

            date = sdf.parse(dateText)

            return date.time
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return 0L
}

fun getDate(timestamp: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp

    val sdf: SimpleDateFormat?

    try {
        sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

        return sdf.format(calendar.time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}

fun Long.getYear(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this

    val sdf: SimpleDateFormat?

    try {
        sdf = SimpleDateFormat("yyyy", Locale.ENGLISH)

        return sdf.format(calendar.time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}

fun Long.getMonth(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this

    val sdf: SimpleDateFormat?

    try {
        sdf = SimpleDateFormat("MM", Locale.ENGLISH)

        return sdf.format(calendar.time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}

fun getMonthName(monthOfYear: Int = -1): String {
    val calendar = Calendar.getInstance().apply {
        if (monthOfYear != -1) {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.MONTH, monthOfYear)
        }
    }

    val sdf: SimpleDateFormat?

    try {
        sdf = SimpleDateFormat("MMM", Locale.ENGLISH)

        return sdf.format(calendar.time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}

fun Long.getDay(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this

    val sdf: SimpleDateFormat?

    try {
        sdf = SimpleDateFormat("dd", Locale.ENGLISH)

        return sdf.format(calendar.time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}

fun String.formatDate(): String? {
    val locale = Locale.getDefault()
    val format: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val formatTo: DateFormat = SimpleDateFormat("MMM dd", locale)
    var date: Date? = null
    try {
        date = format.parse(this)
    } catch (e: ParseException) {
//        TRPLogger.debug(e.message)
    }
    return formatTo.format(date)
}

fun String.formatDateWithShortName(): String? {
    val locale = Locale.getDefault()
    val format: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val formatTo: DateFormat = SimpleDateFormat("dd MMM yyyy", locale)
    var date: Date? = null
    try {
        date = format.parse(this)
    } catch (e: ParseException) {
//        TRPLogger.debug(e.message)
    }
    return formatTo.format(date)
}

fun String.formatDateDay(): String? {
    val locale = Locale.getDefault()
    val format: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val formatTo: DateFormat = SimpleDateFormat("MMM d", locale)
    var date: Date? = null
    try {
        date = format.parse(this)
    } catch (e: ParseException) {
//        TRPLogger.debug(e.message)
    }
    return formatTo.format(date)
}

fun String?.formatTime(): String? {
    if (this == null) return null

    return this.split("T").let {
        if (it.size > 1) {
            it[1].split(":").let {
                if (it.size > 1) {
                    "${it[0]}:${it[1]}"
                } else {
                    ""
                }
            }
        } else {
            ""
        }
    }
}

fun Long.getRequestStartDate(): String {
    val format: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this

    return "${format.format(calendar.time)}T00:00:00"
}

fun Long.getRequestEndDate(): String {
    val format: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.add(Calendar.DAY_OF_MONTH, 6)

    return "${format.format(calendar.time)}T23:59:00"
}

fun afterToday(): String {
    val format: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    val cal = Calendar.getInstance()
    cal.time = Date()
    cal.add(Calendar.DATE, -1)

    return format.format(cal.time)
}

fun Long.formatDate(): String {
    val date = if (this == -1L) {
        System.currentTimeMillis()
    } else {
        this
    }

    val newFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    newFormat.timeZone = TimeZone.getTimeZone("UTC")
    return newFormat.format(Date(date))
}

fun Long.formatTime(): String {
    val date = if (this == -1L) {
        "21:00".hourToMillis()
    } else {
        this
    }

    val newFormat = SimpleDateFormat("HH:mm")
    return newFormat.format(Date(date))
}

fun Long.formatTime24(): String {
    val date = if (this == -1L) {
        "21:00".hourToMillis()
    } else {
        this
    }

    val newFormat = SimpleDateFormat("HH:mm")
    return newFormat.format(Date(date))
}

fun String.hourToMillis(): Long {
    val hourArr = this.split(":").toTypedArray()
    val hourAsInt = hourArr[0].toInt()
    val minuteAsInt = hourArr[1].toInt()
    val calendar = Calendar.getInstance()
    calendar[Calendar.HOUR_OF_DAY] = hourAsInt
    calendar[Calendar.MINUTE] = minuteAsInt
    calendar[Calendar.AM_PM] = calendar[Calendar.AM_PM]
    return calendar.timeInMillis
}

fun getDiffDate(date2: Long, date1: Long): Long {
    val calendar1 = Calendar.getInstance()
    calendar1.timeInMillis = date1
    calendar1[Calendar.HOUR_OF_DAY] = 0
    calendar1[Calendar.MINUTE] = 0
    calendar1[Calendar.SECOND] = 0
    calendar1[Calendar.MILLISECOND] = 0
    val calendar2 = Calendar.getInstance()
    calendar2.timeInMillis = date2
    calendar2[Calendar.HOUR_OF_DAY] = 0
    calendar2[Calendar.MINUTE] = 0
    calendar2[Calendar.SECOND] = 0
    calendar2[Calendar.MILLISECOND] = 0
    val diff = calendar2.time.time - calendar1.time.time
    return if (diff > 0) {
        TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
    } else {
        if (diff <= 0) {
            -1
        } else {
            0
        }
    }
}

fun String.parseDate(): Long {
    val dates: List<String>? = removeLastCharacter(this)?.split("T")

    if (dates != null && dates.size > 1) {
        val dateArr: Array<String> = dates[0].split("-").toTypedArray()
        val year = Integer.valueOf(dateArr[0])
        //month − Value to be used for MONTH field. 0 is January
        //month − Value to be used for MONTH field. 0 is January
        val monthOfYear = Integer.valueOf(dateArr[1]) - 1
        val dayOfMonth = Integer.valueOf(dateArr[2])

        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = monthOfYear
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth

        return calendar.timeInMillis
    }

    return -1L
}

fun String.parseTime(): Long {
    val dates: List<String>? = removeLastCharacter(this)?.split("T")

    if (dates != null && dates.size > 1) {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = dates[1].split(":").toTypedArray()[0].toInt()
        calendar[Calendar.MINUTE] = dates[1].split(":").toTypedArray()[1].toInt()
        calendar[Calendar.AM_PM] = calendar[Calendar.AM_PM]

        return calendar.timeInMillis
    }

    return -1L
}

fun formatDateString(date: Long, time: Long): String {
    val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val timeFormatter: DateFormat = SimpleDateFormat("HH:mm", Locale.US)

    val dateString = dateFormatter.format(Date(date))
    val timeString = timeFormatter.format(Date(time))

    return dateString + "T" + timeString + ":00Z"
}

fun getWeekDaysAsDictionary(): MutableMap<String, Array<String>> {
    val dictionary: MutableMap<String, Array<String>> = HashMap()
    for (day in DayOfWeek.values()) {
        dictionary[day.dayName] = arrayOf()
    }

    return dictionary
}

//returns week days as a dictionary.
fun getDayInNewLine(dayName: String, dayValue: String): String {
    val dayInNewLine = java.lang.StringBuilder()
    dayInNewLine.append(dayName)
    dayInNewLine.append(" ")
    dayInNewLine.append(dayValue)
    return dayInNewLine.toString()
}

fun dayOfWeekContains(name: String): Boolean {
    for (c in DayOfWeek.values()) {
        if (TextUtils.equals(c.name, name)) {
            return true
        }
    }
    return false
}

enum class DayOfWeek(//Getters and Setters
    //Instance Variables
    //Associate a number with the day of the week.
    open val dayNumber: Int, val dayName: String
) {
    //Days of week and values associated with them.
    SUN(1, "Sun") {
        override operator fun next(): DayOfWeek {
            return MON
        }
    },
    MON(2, "Mon") {
        override operator fun next(): DayOfWeek {
            return TUE
        }
    },
    TUE(3, "Tue") {
        override operator fun next(): DayOfWeek {
            return WED
        }
    },
    WED(4, "Wed") {
        override operator fun next(): DayOfWeek {
            return THU
        }
    },
    THU(5, "Thu") {
        override operator fun next(): DayOfWeek {
            return FRI
        }
    },
    FRI(6, "Fri") {
        override operator fun next(): DayOfWeek {
            return SAT
        }
    },
    SAT(7, "Sat") {
        override operator fun next(): DayOfWeek {
            return SUN
        }
    };

    //new toString implementation
    override fun toString(): String {
        return dayName
    }

    //to implement in the enum values.
    open fun next(): DayOfWeek {
        return values()[(ordinal + 1) % values().size]
    }

}

fun String.getBirthDateFromAge(): String {
    val year = Calendar.getInstance().get(Calendar.YEAR)
    val birthYear = year - (this.toIntOrNull() ?: 0)
    return "$birthYear-01-01"
}

fun String.getAgeFromBirthDate(): String {
    val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val cal = Calendar.getInstance()
    cal.time = dateFormatter.parse(this) ?: Date()
    val birthYear = cal.get(Calendar.YEAR)
    val age = Calendar.getInstance().get(Calendar.YEAR) - birthYear
    return age.toString()
}