package com.example.weatherapplication.entities

data class ApiResponseModel(
    val current: CurrentWeatherModel,
    val forecastDays: List<ForecastDayModel>
)