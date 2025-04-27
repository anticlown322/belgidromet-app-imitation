package com.example.weatherapplication.services.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class LocationService(
    private val context: Context,
    private val callback: LocationCallback
) {
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    fun getCurrentLocation() {
        if (!checkPermissions()) {
            callback.onLocationError("Location permissions not granted")
            return
        }

        if (!isLocationEnabled()) {
            callback.onLocationError("Location services disabled")
            return
        }

        val cancellationToken = CancellationTokenSource()

        try {
            // Явная проверка разрешений
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                callback.onLocationError("Location permissions not granted")
                return
            }

            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationToken.token
            ).addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val location = task.result
                    callback.onLocationReceived(location.latitude, location.longitude)
                } else {
                    callback.onLocationError("Unable to get location: ${task.exception?.message}")
                }
            }
        } catch (e: SecurityException) {
            callback.onLocationError("Security exception: ${e.message}")
        } catch (e: Exception) {
            callback.onLocationError("Location error: ${e.message}")
        }
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}