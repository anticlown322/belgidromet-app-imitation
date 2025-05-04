package com.example.weatherapplication.services.location

interface LocationCallback {
    fun onLocationReceived(latitude: Double, longitude: Double, isManualRefresh: Boolean = false)
    fun onLocationError(error: String)
}