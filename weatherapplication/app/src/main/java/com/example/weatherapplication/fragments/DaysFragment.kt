package com.example.weatherapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.MainViewModel
import com.example.weatherapplication.adapters.ForecastDayAdapter
import com.example.weatherapplication.databinding.FragmentDaysBinding
import com.example.weatherapplication.entities.ForecastDayModel

class DaysFragment : Fragment(), ForecastDayAdapter.DayClickListener {
    private lateinit var binding: FragmentDaysBinding
    private lateinit var adapter: ForecastDayAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeData()
    }

    private fun initRecyclerView() = with(binding) {
        adapter = ForecastDayAdapter(this@DaysFragment)
        rcDaysView.layoutManager = LinearLayoutManager(requireContext())
        rcDaysView.adapter = adapter
    }

    private fun observeData() {
        model.liveDataDailyForecast.observe(viewLifecycleOwner) { dailyForecast ->
            adapter.submitList(dailyForecast)
        }
    }

    override fun onDayClick(day: ForecastDayModel) {
        // какое-либо действие при клике
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }
}