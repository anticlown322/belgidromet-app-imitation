package com.example.weatherapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.entities.CurrentWeatherModel
import com.example.weatherapplication.entities.ForecastDayModel
import com.example.weatherapplication.entities.ForecastDetailsModel
import com.example.weatherapplication.entities.ForecastHourModel
import com.example.weatherapplication.entities.WeatherAlert

class MainViewModel : ViewModel() {
    val liveDataCurrent = MutableLiveData<CurrentWeatherModel>()

    val liveDataCurrentDetails = MutableLiveData<ForecastDetailsModel>()

    val liveDataDailyForecast = MutableLiveData<List<ForecastDayModel>>()

    val liveDataHourlyForecast = MutableLiveData<List<ForecastHourModel>>()

    val liveDataAlerts = MutableLiveData<List<WeatherAlert>>()
}