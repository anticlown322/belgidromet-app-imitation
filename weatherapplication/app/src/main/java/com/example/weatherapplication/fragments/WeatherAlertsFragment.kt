package com.example.weatherapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.adapters.WeatherAlertsAdapter
import com.example.weatherapplication.databinding.FragmentAlertsBinding
import com.example.weatherapplication.entities.WeatherAlert

class WeatherAlertsFragment : Fragment() {
    private lateinit var binding: FragmentAlertsBinding

    companion object {
        private const val ARG_ALERTS = "alerts"

        fun newInstance(alerts: List<WeatherAlert>) = WeatherAlertsFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(ARG_ALERTS, ArrayList(alerts))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAlertsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val alerts = arguments?.getParcelableArrayList<WeatherAlert>(ARG_ALERTS) ?: emptyList()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = WeatherAlertsAdapter(alerts)
    }
}