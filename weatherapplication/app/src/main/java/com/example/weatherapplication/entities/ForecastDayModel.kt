package com.example.weatherapplication.entities

data class ForecastDayModel(
    val date: String,
    val condition: String,
    val imageUrl: String,
    val minTempC: Double,
    val maxTempC: Double,
    val hourlyForecasts: List<ForecastHourModel>
)