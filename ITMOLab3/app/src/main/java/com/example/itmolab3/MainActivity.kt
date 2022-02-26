package com.example.itmolab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.itmolab3.cards.CurrentCardsModel
import com.example.itmolab3.cards.CurrentCardsViewModel
import com.example.itmolab3.databinding.ActivityMainBinding
import com.example.itmolab3.ui.Buttons
import com.google.accompanist.appcompattheme.AppCompatTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: CurrentCardsViewModel by viewModels()

        viewModel
            .modelStream
            .observe(this) {
                bindTopCard(it)
                lifecycleScope.launch {
                    delay(100L)
                    bindBottomCard(it)
                }
            }

        binding.root.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                viewModel.swiped()

                when (currentId) {
                    R.id.endLike -> {
                        Toast.makeText(this@MainActivity, "Liked", Toast.LENGTH_SHORT).show()
                        motionLayout.progress = 0f
                        motionLayout.setTransition(R.id.start, R.id.endLike)
                    }
                    R.id.endDislike -> {
                        Toast.makeText(this@MainActivity, "Disliked", Toast.LENGTH_SHORT).show()
                        motionLayout.progress = 0f
                        motionLayout.setTransition(R.id.start, R.id.endDislike)
                    }
                    R.id.endUp -> {
                        Toast.makeText(this@MainActivity, "Skipped", Toast.LENGTH_SHORT).show()
                        motionLayout.progress = 0f
                        motionLayout.setTransition(R.id.start, R.id.middleUp)
                    }
                }


            }
        })

        binding.bottomBar.setContent {
            AppCompatTheme {
                Buttons({ clickOnLike() }, { clickOnDislike() })
            }
        }
    }

    private fun clickOnLike() {
        binding.root.setTransition(R.id.start, R.id.endLike)
        binding.root.transitionToEnd()
    }

    private fun clickOnDislike() {
        binding.root.setTransition(R.id.start, R.id.endDislike)
        binding.root.transitionToEnd()
    }

    private fun bindTopCard(model: CurrentCardsModel) {
        binding.topCardNameText.text = model.top.name
        binding.topCardAvatar.setImageResource(model.top.avatar_src)
        binding.topCardDescriptionText.text = model.top.description

        binding.topCardDescriptionText.setOnClickListener {
            if (model.top.expanded) {
                binding.topCardDescriptionText.maxLines = 3
                model.top.expanded = false
            } else {
                binding.topCardDescriptionText.maxLines = Int.MAX_VALUE
                model.top.expanded = true
            }
        }
    }

    private fun bindBottomCard(model: CurrentCardsModel) {
        binding.bottomCardNameText.text = model.bottom.name
        binding.bottomCardAvatar.setImageResource(model.bottom.avatar_src)
        binding.bottomCardDescriptionText.text = model.bottom.description
    }
}