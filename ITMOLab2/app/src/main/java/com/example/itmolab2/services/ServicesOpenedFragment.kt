package com.example.itmolab2.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.itmolab2.R
import com.example.itmolab2.databinding.ServicesOpenedFragmentBinding

class ServicesOpenedFragment : Fragment() {
    private var _binding: ServicesOpenedFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ServicesOpenedFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val count = arguments?.getInt("count") ?: 0

        binding.textResult.text = getString(R.string.counter_text, count)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}