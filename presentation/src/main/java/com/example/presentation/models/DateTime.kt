package com.example.presentation.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.coroutines.delay
import java.util.*

class DateTime(var value: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DateTime> {
        override fun createFromParcel(parcel: Parcel): DateTime {
            return DateTime(parcel)
        }

        override fun newArray(size: Int): Array<DateTime?> {
            return arrayOfNulls(size)
        }

        suspend fun subscribeDayPassed(value: String): Boolean {
            val timeOfChange = convertStringToCalendar(value)
            val currentTime = getCurrentCalendar()

            if (checkDatesTheSameDay(timeOfChange, currentTime)) {
                val diffHours = currentTime.get(Calendar.HOUR_OF_DAY)
                val diffMinutes = currentTime.get(Calendar.MINUTE)
                val millis = (diffHours * 60 + diffMinutes) * 60 * 1000L
                delay(millis)
                return true
            } else {
                return false
            }
        }

        fun convertStringToCalendar(value: String): Calendar {
            val regexTime = Regex("\\d{2}\\.\\d{2}")
            val isValidDate = regexTime.matches(value)
            if (isValidDate) {
                val timeOfChange = Calendar.getInstance()
                timeOfChange.set(Calendar.HOUR_OF_DAY, value.split('.')[0].toInt())
                timeOfChange.set(Calendar.MINUTE, value.split('.')[1].toInt())
                return timeOfChange
            } else {
                val dateOfChange = Calendar.getInstance()
                dateOfChange.set(Calendar.HOUR_OF_DAY, value.split('.')[0].toInt())
                dateOfChange.set(Calendar.MINUTE, value.split('.')[1].toInt())
                return dateOfChange
            }
        }

        fun convertCalendarToString(dateOfChange: Calendar): String {
            val currentDate = getCurrentCalendar()

            return if (checkDatesTheSameDay(dateOfChange, currentDate)) {
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

        private fun checkDatesTheSameDay(dateOfChange: Calendar, currentDate: Calendar): Boolean {
            return (dateOfChange.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR)
                    && dateOfChange.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH)
                    && dateOfChange.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH))
        }

        fun getCurrentCalendar(): Calendar {
            return Calendar.getInstance()
        }
    }
}