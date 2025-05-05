package com.example.weatherapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weatherapplication.MainViewModel
import com.example.weatherapplication.adapters.ForecastDetailsAdapter
import com.example.weatherapplication.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var adapter: ForecastDetailsAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeData()
    }

    private fun initRecyclerView() = with(binding) {
        adapter = ForecastDetailsAdapter()
        rcDetailsView.layoutManager = GridLayoutManager(requireContext(), 2)
        rcDetailsView.adapter = adapter
    }

    private fun observeData() {
        model.liveDataCurrentDetails.observe(viewLifecycleOwner) { details ->
            details?.let { adapter.submitDetails(it) }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailsFragment()
    }
}