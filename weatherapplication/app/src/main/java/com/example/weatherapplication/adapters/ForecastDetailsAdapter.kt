package com.example.weatherapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.databinding.ItemDetailBinding
import com.example.weatherapplication.entities.ForecastDetailsModel

class ForecastDetailsAdapter : RecyclerView.Adapter<ForecastDetailsAdapter.DetailsViewHolder>() {

    private var details: ForecastDetailsModel? = null

    inner class DetailsViewHolder(private val binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(detailItem: Pair<String, String>) {
            binding.detailName.text = detailItem.first
            binding.detailValue.text = detailItem.second
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val binding = ItemDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        details?.let {
            val detailItems = listOf(
                "Humidity" to "${it.humidity}%",
                "Wind Speed" to "${it.windSpeed} kph",
                "Wind Direction" to it.windDirection,
                "Pressure" to "${it.pressure} mb",
                "Precipitation" to "${it.precipitation} mm",
                "UV Index" to it.uvIndex.toString(),
                "Visibility" to "${it.visibility} km",
                "Feels Like" to "${it.feelsLike}°C",
                "Gust Speed" to "${it.gustSpeed} kph",
                "Cloud Cover" to "${it.cloudCover}%"
            )
            holder.bind(detailItems[position])
        }
    }

    override fun getItemCount(): Int = 10

    fun submitDetails(newDetails: ForecastDetailsModel) {
        details = newDetails
        notifyDataSetChanged()
    }
}