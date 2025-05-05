package com.example.weatherapplication.entities

data class ForecastDetailsModel(
    val humidity: Int,
    val windSpeed: Double,
    val windDirection: String,
    val pressure: Double,
    val precipitation: Double,
    val uvIndex: Double,
    val visibility: Double,
    val feelsLike: Double,
    val gustSpeed: Double,
    val cloudCover: Int
)