package com.example.forcastweather.network

import com.example.forcastweather.model.Forecastday
import com.example.forcastweather.model.Weather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("forecast.json?")
    fun getDataForcastOneWeek(
        @Query("key") api_key: String,
        @Query("q") location: String,
        @Query("days") count_days: Int
        ): Observable<Weather>


    @GET("forecast.json?")
    fun getDataForcastToday(
        @Query("key") api_key: String,
        @Query("q") location: String
    ): Observable<Weather>
}