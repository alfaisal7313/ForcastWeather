package com.example.forcastweather.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast(
    @SerializedName("forecastday")
    val forecastday: List<Forecastday>
):Parcelable