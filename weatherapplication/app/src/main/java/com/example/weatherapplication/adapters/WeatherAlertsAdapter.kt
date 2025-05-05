package com.example.weatherapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.ItemWeatherAlertBinding
import com.example.weatherapplication.entities.WeatherAlert

class WeatherAlertsAdapter(private val alerts: List<WeatherAlert>) :
    RecyclerView.Adapter<WeatherAlertsAdapter.AlertViewHolder>() {

    inner class AlertViewHolder(binding: ItemWeatherAlertBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val title = binding.tvAlertTitle
        private val description = binding.tvAlertDescription
        private val severity = binding.tvAlertSeverity
        private val icon = binding.ivAlertIcon
        private val time = binding.tvAlertTime

        fun bind(alert: WeatherAlert) {
            title.text = alert.title
            description.text = alert.message
            time.text = alert.time

            when (alert.severity.lowercase()) {
                "high" -> {
                    severity.text = "HIGH"
                    severity.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.alert_high))
                }
                "medium" -> {
                    severity.text = "MEDIUM"
                    severity.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.alert_medium))
                }
                else -> {
                    severity.text = "LOW"
                    severity.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.alert_low))
                }
            }

            // Можно установить разные иконки в зависимости от типа предупреждения
            val iconRes = when {
                alert.title.contains("wind", ignoreCase = true) -> R.drawable.ic_wind
                alert.title.contains("rain", ignoreCase = true) -> R.drawable.ic_rain
                else -> R.drawable.ic_warning
            }
            icon.setImageResource(iconRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val binding = ItemWeatherAlertBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return AlertViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        holder.bind(alerts[position])
    }

    override fun getItemCount() = alerts.size
}