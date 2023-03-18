package com.example.presentation.models

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class Note(
    val id: Int,
    var title: String,
    var description: String,
    var dateOfChange: DateTime
) : Parcelable {

    fun subscribeDayPassed(): Boolean = runBlocking {
        DateTime.subscribeDayPassed(dateOfChange.value)
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(DateTime::class.java.classLoader)!!
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeParcelable(dateOfChange, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}