package com.example.forcastweather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.forcastweather.R
import com.example.forcastweather.model.Forecastday
import com.example.forcastweather.view.MainViewModel
import kotlinx.android.synthetic.main.item_history.view.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class WeatherAdapter(viewModel: MainViewModel, lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    lateinit var weatherDayList: List<Forecastday>

    init {
        viewModel.setDataWeather().observe(lifecycleOwner, Observer {
            if (it != null) {
                weatherDayList = it.forecast.forecastday
                notifyDataSetChanged()
            }
        })
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): WeatherViewHolder =
        WeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_history,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = weatherDayList.size ?: 0

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bindView(weatherDayList.get(position))
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(get: Forecastday) {

            val inputPattern = SimpleDateFormat("yyyy-MM-dd")
            val outputPattern = SimpleDateFormat("EEEE")

            itemView.tv_history_day.text = outputPattern.format(inputPattern.parse(get.date))
            itemView.tv_history_temperature.text = get.day.maxtempC.roundToInt().toString() +"\u00B0"
        }
    }
}