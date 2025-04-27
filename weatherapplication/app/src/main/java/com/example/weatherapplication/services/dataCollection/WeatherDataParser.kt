package com.example.weatherapplication.services.dataCollection

import com.example.weatherapplication.entities.CurrentWeatherModel
import com.example.weatherapplication.entities.ForecastDayModel
import com.example.weatherapplication.entities.ForecastHourModel
import com.example.weatherapplication.entities.ApiResponseModel
import org.json.JSONArray
import org.json.JSONObject

class WeatherDataParser {

    fun parseWeatherResponse(response: String): ApiResponseModel {
        val mainObject = JSONObject(response)
        val current = parseCurrentWeather(mainObject.getJSONObject("current"))
        val forecastDays = parseForecastDays(mainObject.getJSONObject("forecast"))

        return ApiResponseModel(current, forecastDays)
    }

    private fun parseCurrentWeather(currentObject: JSONObject): CurrentWeatherModel {
        val conditionObject = currentObject.getJSONObject("condition")

        return CurrentWeatherModel(
            localTime = currentObject.getString("last_updated"),
            condition = conditionObject.getString("text"),
            tempC = currentObject.getDouble("temp_c"),
            imageUrl = conditionObject.getString("icon")
        )
    }

    private fun parseForecastDays(forecastObject: JSONObject): List<ForecastDayModel> {
        val forecastDays = forecastObject.getJSONArray("forecastday")
        val daysList = mutableListOf<ForecastDayModel>()

        for (i in 0 until forecastDays.length()) {
            val dayObject = forecastDays.getJSONObject(i)
            val dayData = dayObject.getJSONObject("day")
            val condition = dayData.getJSONObject("condition")

            daysList.add(
                ForecastDayModel(
                    date = dayObject.getString("date"),
                    condition = condition.getString("text"),
                    imageUrl = condition.getString("icon"),
                    minTempC = dayData.getDouble("mintemp_c"),
                    maxTempC = dayData.getDouble("maxtemp_c"),
                    hourlyForecasts = parseHourlyForecast(dayObject.getJSONArray("hour"))
                )
            )
        }

        return daysList
    }

    private fun parseHourlyForecast(hoursArray: JSONArray): List<ForecastHourModel> {
        val hourlyList = mutableListOf<ForecastHourModel>()

        for (i in 0 until hoursArray.length()) {
            val hourObject = hoursArray.getJSONObject(i)
            val condition = hourObject.getJSONObject("condition")

            hourlyList.add(
                ForecastHourModel(
                    time = hourObject.getString("time"),
                    condition = condition.getString("text"),
                    imageUrl = condition.getString("icon"),
                    tempC = hourObject.getDouble("temp_c")
                )
            )
        }

        return hourlyList
    }
}