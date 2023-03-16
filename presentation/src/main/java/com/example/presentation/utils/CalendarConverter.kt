package com.example.presentation.utils

import java.util.*

object CalendarConverter {

    fun convertToString(dateOfChange: Calendar): String {
        val currentDate = getCurrentCalendar()

        return if (dateOfChange.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR)
            && dateOfChange.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH)
            && dateOfChange.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)
        ) {
            String.format(
                "%02d.%02d",
                dateOfChange.get(Calendar.HOUR_OF_DAY),
                dateOfChange.get(Calendar.MINUTE)
            )
        } else {
            String.format(
                "%02d.%02d.%04d",
                dateOfChange.get(Calendar.DAY_OF_MONTH),
                dateOfChange.get(Calendar.MONTH) + 1,
                dateOfChange.get(Calendar.YEAR)
            )
        }
    }

    fun getCurrentCalendar(): Calendar {
        return Calendar.getInstance()
    }
}