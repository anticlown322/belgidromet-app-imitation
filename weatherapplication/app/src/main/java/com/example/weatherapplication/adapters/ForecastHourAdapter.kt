package com.example.weatherapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.databinding.ItemHourBinding
import com.example.weatherapplication.entities.ForecastHourModel
import com.squareup.picasso.Picasso

class ForecastHourAdapter(
    private val listener: HourClickListener? = null
) : ListAdapter<ForecastHourModel, ForecastHourAdapter.HourViewHolder>(HourDiffCallback()) {

    interface HourClickListener {
        fun onHourClick(hour: ForecastHourModel)
    }

    class HourViewHolder(
        private val binding: ItemHourBinding,
        private val listener: HourClickListener?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hour: ForecastHourModel) {
            with(binding) {
                // Форматируем время (оставляем только часы:минуты)
                tvTime.text = hour.time.substringAfterLast(" ").take(5)
                tvTemp.text = "${hour.tempC}°C"
                tvCondition.text = hour.condition
                Picasso.get().load("https:${hour.imageUrl}").into(ivIcon)

                root.setOnClickListener { listener?.onHourClick(hour) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val binding = ItemHourBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HourViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class HourDiffCallback : DiffUtil.ItemCallback<ForecastHourModel>() {
        override fun areItemsTheSame(
            oldItem: ForecastHourModel,
            newItem: ForecastHourModel
        ): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(
            oldItem: ForecastHourModel,
            newItem: ForecastHourModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}