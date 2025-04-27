package com.example.weatherapplication.services.dataCollection

interface WeatherCallback {
    fun onSuccess(result: String)
    fun onError(error: String)
}