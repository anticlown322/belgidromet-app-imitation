package com.example.weatherapplication.services.dataCollection

import com.example.weatherapplication.entities.CurrentWeatherModel
import com.example.weatherapplication.entities.ForecastDayModel
import com.example.weatherapplication.entities.ForecastHourModel
import com.example.weatherapplication.entities.ApiResponseModel
import com.example.weatherapplication.entities.WeatherAlert
import org.json.JSONArray
import org.json.JSONObject

class WeatherDataParser {

    fun parseWeatherResponse(response: String): ApiResponseModel {
        val mainObject = JSONObject(response)
        val current = parseCurrentWeather(mainObject.getJSONObject("current"))
        val forecastDays = parseForecastDays(mainObject.getJSONObject("forecast"))
        val alerts = generateWeatherAlerts(mainObject)

        return ApiResponseModel(current, forecastDays, alerts)
    }

    private fun generateWeatherAlerts(weatherData: JSONObject): List<WeatherAlert> {
        val alerts = mutableListOf<WeatherAlert>()
        val current = weatherData.getJSONObject("current")
        val forecast = weatherData.getJSONObject("forecast")
        val forecastDay = forecast.getJSONArray("forecastday").getJSONObject(0).getJSONObject("day")

        // Извлекаем необходимые данные из JSON
        val windKph = current.getDouble("wind_kph")
        val precipMm = forecastDay.getDouble("totalprecip_mm")
        val maxTempC = forecastDay.getDouble("maxtemp_c")
        val minTempC = forecastDay.getDouble("mintemp_c")
        val humidity = current.getDouble("humidity")
        val conditionCode = current.getJSONObject("condition").getInt("code")
        val lastUpdated = current.getString("last_updated")
        val date = forecast.getJSONArray("forecastday").getJSONObject(0).getString("date")

        //влажность %
        if (humidity < 30 || humidity > 70) {
            val description = when {
                humidity < 40 -> "Low humidity of $humidity% expected. Dry air may cause discomfort."
                else -> "High humidity of $humidity% expected. Air feels stuffy and oppressive."
            }

            alerts.add(
                WeatherAlert(
                    title = "Humidity Warning",
                    description = description,
                    severity = "High",
                    time = lastUpdated,
                    message = "Humidity Alert: $humidity%"
                )
            )
        }

        // скорость ветра км/ч
        if (windKph > 30) {
            alerts.add(
                WeatherAlert(
                    title = "High Wind Warning",
                    description = "Strong winds of $windKph kph expected",
                    severity = "Medium",
                    time = lastUpdated,
                    message = "High Wind Warning"

                )
            )
        }

        //осадки мм
        if (precipMm > 10) {
            alerts.add(
                WeatherAlert(
                    title = "Heavy Rain",
                    description = "Heavy rainfall expected ($precipMm mm)",
                    severity = "High",
                    time = date,
                    message = "Heavy Rain"
                )
            )
        }

        //макс температура
        if (maxTempC > 35) {
            alerts.add(
                WeatherAlert(
                    title = "Heat Wave",
                    description = "Extreme heat expected (up to $maxTempC°C)",
                    severity = "High",
                    time = date,
                    message = "Extreme heat expected (up to $maxTempC°C)"
                )
            )
        }
        //минимальная температура
        if (minTempC < -10) {
            alerts.add(
                WeatherAlert(
                    title = "Freezing Cold",
                    description = "Extreme cold expected (down to $minTempC°C)",
                    severity = "High",
                    time = date,
                    message = "Freezing Cold"
                )
            )
        }

        // Коды погодных условий для грозы (может отличаться в вашем API)
        val thunderstormCodes = listOf(1087, 1273, 1276, 1282)
        if (conditionCode in thunderstormCodes) {
            alerts.add(
                WeatherAlert(
                    title = "Thunderstorm Alert",
                    description = "Thunderstorm expected",
                    severity = "High",
                    time = lastUpdated,
                    message = "Thunderstorm Alert"
                )
            )
        }

        return alerts
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