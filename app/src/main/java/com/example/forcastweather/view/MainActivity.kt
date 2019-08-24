package com.example.forcastweather.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forcastweather.R
import com.example.forcastweather.adapter.WeatherAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        rv_history.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )
        rv_history.setHasFixedSize(true)
        rv_history.adapter = viewModel?.let { WeatherAdapter(viewModel!!, this) }
        rv_history.layoutManager = LinearLayoutManager(this)

        observableViewModel()

        swipe_refresh.setOnRefreshListener {
            observableViewModel()
        }
    }

    private fun observableViewModel() {
        viewModel?.getStatusWeather()?.observe(this, Observer {isLoading ->
            if (isLoading) {
                swipe_refresh.isRefreshing = true
                rv_history.visibility = View.GONE
            }else{
                swipe_refresh.isRefreshing = false
            }
        })

        viewModel?.setDataWeather()?.observe(this, Observer { data ->
            let {
                tv_location.text = data.location.name
                tv_condition.text = data.current.condition.text
                tv_temperature.text = data.current.tempC.roundToInt().toString() + "\u00B0"
            }
            if (data != null) rv_history.visibility = View.VISIBLE
        })

        viewModel?.setErrorWeather()?.observe(this, Observer {
            if (it != null) {
                if (it) {
                    rv_history.visibility = View.GONE
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        observableViewModel()
    }
}
