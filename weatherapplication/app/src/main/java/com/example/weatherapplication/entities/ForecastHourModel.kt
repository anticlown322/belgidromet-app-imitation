package com.example.weatherapplication.entities

data class ForecastHourModel(
    val time: String,
    val condition: String,
    val imageUrl: String,
    val tempC: Double
)