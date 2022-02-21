package com.example.itmolab2.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.itmolab2.R
import com.example.itmolab2.databinding.ServicesFragmentBinding

class ServicesFragment : Fragment() {
    private var _binding: ServicesFragmentBinding? = null
    private val binding get() = _binding!!
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ServicesFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.servicesShowCounterButton.setOnClickListener {
            val bundle = bundleOf("count" to count)
            findNavController().navigate(
                R.id.action_servicesFragment_to_servicesOpenedFragment,
                bundle
            )
        }

        binding.servicesCounterButton.setOnClickListener {
            count++
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        count = 0
        _binding = null
    }
}