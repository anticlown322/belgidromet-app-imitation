package com.example.weatherapplication.services.dataCollection

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class WeatherService(
    private val context: Context
) {
    private val API_KEY = "5d507d9645b94bbf88f153248252304"

    fun requestWeatherDataByCity(city: String, callback: WeatherCallback) {
        val url =
            "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY&q=$city&days=10" +
                    "" +
                    "&aqi=no&alerts=no"

        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result -> callback.onSuccess(result) },
            { error -> callback.onError(error.toString()) }
        )
        queue.add(request)
    }

    fun requestWeatherDataByCoords(latitude: Double, longitude: Double, callback: WeatherCallback) {
        val url =
            "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY&q=$latitude,$longitude&days=10" +
                    "" +
                    "&aqi=no&alerts=no"

        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result -> callback.onSuccess(result) },
            { error -> callback.onError(error.toString()) }
        )
        queue.add(request)
    }
}