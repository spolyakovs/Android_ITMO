package com.example.itmolab2.services


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.itmolab2.MainActivity
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
            val intent = Intent(this.context, ServicesOpenedActivity::class.java).apply {
                putExtra("count", count)
            }
            startActivity(intent)
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