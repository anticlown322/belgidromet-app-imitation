package com.example.weatherapplication.entities

//

import android.os.Parcel
import android.os.Parcelable

data class WeatherAlert(
    val title: String,
    val description: String,
    val severity: String,
    val time: String,
    val message: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(severity)
        parcel.writeString(time)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherAlert> {
        override fun createFromParcel(parcel: Parcel): WeatherAlert {
            return WeatherAlert(parcel)
        }

        override fun newArray(size: Int): Array<WeatherAlert?> {
            return arrayOfNulls(size)
        }
    }
}