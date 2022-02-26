package com.example.itmolab3

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.itmolab3.cards.CurrentCardsModel
import com.example.itmolab3.cards.CurrentCardsViewModel
import com.example.itmolab3.databinding.ActivityMainBinding
import com.example.itmolab3.ui.Buttons
import com.google.accompanist.appcompattheme.AppCompatTheme

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)
            .get(CurrentCardsViewModel::class.java)

        viewModel
            .modelStream
            .observe(this, Observer {
                bindCards(it)
            })

        binding.root.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                when (currentId) {
                    R.id.endLike -> {
                        motionLayout.progress = 0f
                        motionLayout.setTransition(R.id.start, R.id.endLike)
                        viewModel.swiped()
                    }
                    R.id.endDislike -> {
                        motionLayout.progress = 0f
                        motionLayout.setTransition(R.id.start, R.id.endDislike)
                        viewModel.swiped()
                    }
                    R.id.endUp -> {
                        motionLayout.progress = 0f
                        motionLayout.setTransition(R.id.start, R.id.middleUp)
                        viewModel.swiped()
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

    private fun bindCards(model: CurrentCardsModel) {
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


        binding.bottomCardNameText.text = model.bottom.name
        binding.bottomCardAvatar.setImageResource(model.bottom.avatar_src)
        binding.bottomCardDescriptionText.text = model.bottom.description
    }
}