package com.example.foodbot.utils

import com.example.foodbot.R

enum class Day(val index: Int, val resId: Int) {
    MONDAY(0, R.string.monday),
    TUESDAY(1, R.string.tuesday),
    WEDNESDAY(2, R.string.wednesday),
    THURSDAY(3, R.string.thursday),
    FRIDAY(4, R.string.friday),
    SATURDAY(5, R.string.saturday),
    SUNDAY(6, R.string.sunday);

    companion object {
        fun getRes(index: Int): Int? = entries.firstOrNull { index == it.index }?.resId
    }
}