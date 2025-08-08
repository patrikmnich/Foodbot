package com.example.foodbot.utils

import com.example.foodbot.R
import java.time.DayOfWeek

enum class Day(val index: Int, val resId: Int) {
    MONDAY(0, R.string.monday),
    TUESDAY(1, R.string.tuesday),
    WEDNESDAY(2, R.string.wednesday),
    THURSDAY(3, R.string.thursday),
    FRIDAY(4, R.string.friday),
    SATURDAY(5, R.string.saturday),
    SUNDAY(6, R.string.sunday);

    companion object {
        fun getRes(index: Int): Int = entries.first { index == it.index }.resId

        fun getFromDayOfWeek(day: DayOfWeek): Day {
            return when (day) {
                DayOfWeek.MONDAY -> MONDAY
                DayOfWeek.TUESDAY -> TUESDAY
                DayOfWeek.WEDNESDAY -> WEDNESDAY
                DayOfWeek.THURSDAY -> THURSDAY
                DayOfWeek.FRIDAY -> FRIDAY
                DayOfWeek.SATURDAY -> SATURDAY
                DayOfWeek.SUNDAY -> SUNDAY
            }
        }
    }
}