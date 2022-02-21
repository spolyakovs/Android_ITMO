package com.example.itmolab2.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.itmolab2.R
import com.example.itmolab2.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.homeShowCounterButton.setOnClickListener {
            val bundle = bundleOf("count" to count)
            findNavController().navigate(
                R.id.action_homeFragment_to_homeOpenedFragment,
                bundle
            )
        }

        binding.homeCounterButton.setOnClickListener {
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