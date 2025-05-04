package com.example.weatherapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentCitySearchBinding

class CitySearchFragment : Fragment() {
    private var _binding: FragmentCitySearchBinding? = null
    private val binding get() = _binding!!

    // Используем конкретный тип адаптера
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var cities: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitySearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cities = resources.getStringArray(R.array.popular_cities).toList()

        // Создаем адаптер с явным указанием типа
        adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            cities
        )

        // Правильная установка адаптера
        binding.actvCity.setAdapter(adapter)

        binding.actvCity.apply {
            threshold = 1
            setOnItemClickListener { _, _, position, _ ->
                val selectedCity = adapter.getItem(position) as String
                returnToMainFragment(selectedCity)
            }
        }

        binding.ibClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun returnToMainFragment(city: String) {
        parentFragmentManager.setFragmentResult(
            "city_search_result",
            bundleOf("city" to city)
        )
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = CitySearchFragment()
    }
}