package com.example.itmolab2.services

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.itmolab2.R
import com.example.itmolab2.databinding.ServicesOpenedActivityBinding

class ServicesOpenedActivity : AppCompatActivity() {
    private lateinit var binding: ServicesOpenedActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ServicesOpenedActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val count = savedInstanceState?.getInt("count") ?: 0

        binding.textResult.text = getString(R.string.counter_text, count)
    }
}