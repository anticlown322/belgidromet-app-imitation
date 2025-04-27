package com.example.weatherapplication.services.permissions

interface PermissionCallback {
    fun onPermissionGranted()
    fun onPermissionDenied()
}