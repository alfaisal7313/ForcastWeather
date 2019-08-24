//package com.example.forcastweather.utils
//
//import android.annotation.SuppressLint
//import com.example.forcastweather.BuildConfig.ApiKey
//import com.example.forcastweather.model.Weather
//import com.example.forcastweather.network.NetworkConfig
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//
//object DataRemoteSource : DataSource {
//    private val api_service = NetworkConfig().getApi()
//
//    @SuppressLint("CheckResult")
//    override fun getDataForcast(callback: DataSource.GetDataCallback) {
//        api_service.getDataForcastToday(ApiKey, Constant.LOCATION_WEATHER)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe {
//                run {
//                    if (it.location != null){
//                        val weather = Weather(
//                            it.location,
//                            it.current,
//                            it.forecast,
//                            it.alert
//                        )
//                        callback.onDataLoaded(weather)
//                    }else{
//                        callback.onNotAvaileble()
//                    }
//                }
//            }
//    }
//}