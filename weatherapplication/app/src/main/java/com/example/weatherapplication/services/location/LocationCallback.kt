package com.example.weatherapplication.services.location

interface LocationCallback {
    fun onLocationReceived(latitude: Double, longitude: Double)
    fun onLocationError(error: String)
}