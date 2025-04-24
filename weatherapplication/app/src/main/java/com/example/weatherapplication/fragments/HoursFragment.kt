package com.example.weatherapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.R
import com.example.weatherapplication.adapters.VpAdapter
import com.example.weatherapplication.adapters.WeatherAdapter
import com.example.weatherapplication.adapters.WeatherModel
import com.example.weatherapplication.databinding.FragmentHoursBinding

class HoursFragment : Fragment() {

    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
    }

    private fun initRcView() = with(binding){
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        rcView.adapter = adapter
        val list = listOf(
            WeatherModel(
                "", "12:00", "Sunny",
                "25*", "","",
                "",""
            ),
            WeatherModel(
                "", "13:00", "Sunny",
                "27*", "","",
                "",""
            ),
            WeatherModel(
                "", "14:00", "Sunny",
                "35*", "","",
                "",""
            )
        )

        if (list.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            rcView.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            rcView.visibility = View.VISIBLE
            adapter.submitList(list)
        }

        adapter.submitList(list)
    }


    companion object {

        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}