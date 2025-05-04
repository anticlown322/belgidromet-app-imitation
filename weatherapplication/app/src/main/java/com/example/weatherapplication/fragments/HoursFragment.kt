package com.example.weatherapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.MainViewModel
import com.example.weatherapplication.adapters.ForecastHourAdapter
import com.example.weatherapplication.databinding.FragmentHoursBinding
import com.example.weatherapplication.entities.ForecastHourModel

class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: ForecastHourAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeData()
    }

    private fun initRecyclerView() = with(binding) {
        adapter = ForecastHourAdapter(object : ForecastHourAdapter.HourClickListener {
            override fun onHourClick(hour: ForecastHourModel) {
                // Обработка клика по часу (если не понадобится, то можно снести)
            }
        })

        rcHoursView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        rcHoursView.adapter = adapter
    }

    private fun observeData() {
        model.liveDataHourlyForecast.observe(viewLifecycleOwner) { hourlyForecast ->
            adapter.submitList(hourlyForecast)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}