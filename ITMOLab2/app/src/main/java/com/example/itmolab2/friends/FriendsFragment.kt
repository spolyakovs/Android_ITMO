package com.example.itmolab2.friends

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.itmolab2.R
import com.example.itmolab2.databinding.FriendsFragmentBinding

class FriendsFragment : Fragment() {
    private var _binding: FriendsFragmentBinding? = null
    private val binding get() = _binding!!
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FriendsFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.friendsShowCounterButton.setOnClickListener {
            val bundle = bundleOf("count" to count)
            findNavController().navigate(
                R.id.action_friendsFragment_to_friendsOpenedFragment,
                bundle
            )
        }

        binding.friendsCounterButton.setOnClickListener {
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