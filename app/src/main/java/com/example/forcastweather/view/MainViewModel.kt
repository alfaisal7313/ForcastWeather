package com.example.forcastweather.view

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forcastweather.BuildConfig.ApiKey
import com.example.forcastweather.model.Weather
import com.example.forcastweather.network.NetworkConfig
import com.example.forcastweather.utils.Constant.Companion.LOCATION_WEATHER
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.internal.util.NotificationLite.disposable



class MainViewModel : ViewModel() {
    private var data = MutableLiveData<Weather>()
    private var status = MutableLiveData<Boolean>()
    private var error = MutableLiveData<Boolean>()
    private var disposable: CompositeDisposable? = null
    private val call = NetworkConfig().getApi()
        .getDataForcastOneWeek(
            api_key = ApiKey,
            location = LOCATION_WEATHER,
            count_days = 7
        )

    init {
        disposable = CompositeDisposable()
        getDataWeather()
    }

    @SuppressLint("CheckResult")
    private fun getDataWeather() {
        status.value = true

        val response =  call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Weather>() {
                override fun onComplete() {
                    println("onComplete")
                    status.value = false
                    error.value = false
                }

                override fun onNext(dt_weather: Weather) {
                    error.value = false
                    data.value = dt_weather
                    status.value = false

                }

                override fun onError(e: Throwable) {
                    println("onError ${e.message}")
                    error.value = true
                    status.value = false
                }
            })
        disposable?.add(response)
    }

    fun setDataWeather(): MutableLiveData<Weather> {
        return data
    }

    fun setErrorWeather(): MutableLiveData<Boolean> {
        return error
    }

    fun getStatusWeather(): MutableLiveData<Boolean> {
        return status
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null){
            disposable?.clear()
            disposable = null
        }
    }
}