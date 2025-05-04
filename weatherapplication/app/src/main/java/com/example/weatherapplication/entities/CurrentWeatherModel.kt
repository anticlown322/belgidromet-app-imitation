package com.example.weatherapplication.entities

data class CurrentWeatherModel(
    val localTime: String,
    val condition: String,
    val tempC: Double,
    val imageUrl: String,
    val isDay: Boolean = true,
    val city: String
)