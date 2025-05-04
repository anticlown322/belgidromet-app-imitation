package com.example.weatherapplication.fragments

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapplication.MainViewModel
import com.example.weatherapplication.R
import com.example.weatherapplication.adapters.WeatherPagerAdapter
import com.example.weatherapplication.databinding.FragmentMainBinding
import com.example.weatherapplication.dialogs.DialogListener
import com.example.weatherapplication.dialogs.LocationSettingsDialog
import com.example.weatherapplication.services.dataCollection.WeatherCallback
import com.example.weatherapplication.services.dataCollection.WeatherDataParser
import com.example.weatherapplication.services.dataCollection.WeatherService
import com.example.weatherapplication.services.location.LocationCallback
import com.example.weatherapplication.services.location.LocationService
import com.example.weatherapplication.services.permissions.PermissionCallback
import com.example.weatherapplication.services.permissions.PermissionService
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class MainFragment : Fragment(), PermissionCallback, LocationCallback {
    private lateinit var binding: FragmentMainBinding
    private lateinit var permissionService: PermissionService
    private lateinit var locationService: LocationService
    private lateinit var weatherService: WeatherService

    private val model: MainViewModel by activityViewModels()
    private val weatherParser = WeatherDataParser()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDependencies()
        setupUI()
        updateCurrentCard()
        setupCitySearchResultListener()
    }

    override fun onResume() {
        super.onResume()
        checkLocation()
    }

    private fun initDependencies() {
        permissionService = PermissionService.with(this)
        locationService = LocationService(requireContext(), this)
        weatherService = WeatherService(requireContext())
    }

    // UI
    private fun setupUI() = with(binding) {
        // Настройка ViewPager и TabLayout
        val fragmentList = listOf(
            HoursFragment.newInstance(),
            DaysFragment.newInstance()
        )

        val adapter = WeatherPagerAdapter(activity as FragmentActivity, fragmentList)
        vp.adapter = adapter
        setupTabLayout(tabLayout, vp)

        // Обработчики кликов
        ibRefresh.setOnClickListener { checkLocation() }
        ibSearch.setOnClickListener { showCitySearch() }
    }

    private fun setupTabLayout(tabLayout: TabLayout, viewPager: ViewPager2) {
        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.text = listOf("Hours", "Days")[pos]
        }.attach()
    }

    private fun updateCurrentCard() = with(binding) {
        model.liveDataCurrent.observe(viewLifecycleOwner) { current ->
            tvDate.text = current.localTime
            tvCurrentTemp.text = "${current.tempC}°C"
            tvCondition.text = current.condition
            Picasso.get().load("https:" + current.imageUrl).into(imWeather)

            model.liveDataDailyForecast.value?.firstOrNull()?.let {
                tvMaxMin.text = "${it.maxTempC}°C / ${it.minTempC}°C"
            }
        }
    }

    // Вызовы бизнес-логики
    private fun requestWeatherData(city: String) {
        weatherService.requestWeatherData(city, object : WeatherCallback {

            override fun onSuccess(result: String) {
                val response = weatherParser.parseWeatherResponse(result)

                model.liveDataCurrent.value = response.current
                model.liveDataDailyForecast.value = response.forecastDays

                response.forecastDays.firstOrNull()?.let {
                    model.liveDataHourlyForecast.value = it.hourlyForecasts
                }
            }

            override fun onError(error: String) {
                Toast.makeText(context, "Weather error: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupCitySearchResultListener() {
        parentFragmentManager.setFragmentResultListener(
            "city_search_result",
            viewLifecycleOwner
        ) { _, bundle ->
            val city = bundle.getString("city") ?: return@setFragmentResultListener
            requestWeatherData(city)
        }
    }

    private fun showCitySearch() {
        val searchFragment = CitySearchFragment.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, searchFragment)
            .addToBackStack("city_search")
            .commit()
    }

    private fun showLocationErrorDialog(error: String) {
        LocationSettingsDialog.show(
            requireContext(),
            listener = object : DialogListener {
                override fun onPositiveButtonClicked(data: Any?) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }

                // реализация по умолчанию
                override fun onNegativeButtonClicked() = Unit
            }
        )
    }

    private fun checkLocation() {
        if (permissionService.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            locationService.getCurrentLocation()
        } else {
            permissionService.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Location callbacks
    override fun onLocationReceived(latitude: Double, longitude: Double) {
        requestWeatherData("$latitude,$longitude")
    }

    override fun onLocationError(error: String) {
        showLocationErrorDialog(error)
    }

    // Permission callbacks
    override fun onPermissionGranted() {
        checkLocation()
    }

    override fun onPermissionDenied() {
        Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}