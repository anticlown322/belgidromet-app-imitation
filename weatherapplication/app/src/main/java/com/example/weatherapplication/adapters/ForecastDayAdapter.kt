package com.example.weatherapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.databinding.ItemDayBinding
import com.example.weatherapplication.entities.ForecastDayModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

class ForecastDayAdapter(
    private val listener: DayClickListener? = null
) : ListAdapter<ForecastDayModel, ForecastDayAdapter.DayViewHolder>(DayDiffCallback()) {

    interface DayClickListener {
        fun onDayClick(day: ForecastDayModel)
    }

    class DayViewHolder(
        private val binding: ItemDayBinding,
        private val listener: DayClickListener?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: ForecastDayModel) {
            with(binding) {
                // Форматируем дату в название дня недели
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = dateFormat.parse(day.date)
                tvDay.text = SimpleDateFormat("EEEE", Locale.getDefault()).format(date)

                tvTemp.text = "${day.maxTempC}°C / ${day.minTempC}°C"
                tvCondition.text = day.condition
                Picasso.get().load("https:${day.imageUrl}").into(ivIcon)

                root.setOnClickListener { listener?.onDayClick(day) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = ItemDayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DayViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DayDiffCallback : DiffUtil.ItemCallback<ForecastDayModel>() {
        override fun areItemsTheSame(
            oldItem: ForecastDayModel,
            newItem: ForecastDayModel
        ): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(
            oldItem: ForecastDayModel,
            newItem: ForecastDayModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}