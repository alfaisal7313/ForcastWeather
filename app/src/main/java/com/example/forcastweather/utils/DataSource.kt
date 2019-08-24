package com.example.forcastweather.utils

import com.example.forcastweather.model.Weather

interface DataSource {
    fun getDataForcast(callback: GetDataCallback)

    interface GetDataCallback {
        fun onDataLoaded(weather: Weather)
        fun onNotAvaileble()
        fun onError(massage: String)
    }
}
