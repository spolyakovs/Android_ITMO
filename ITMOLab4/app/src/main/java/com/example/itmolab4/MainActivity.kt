package com.example.itmolab4

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itmolab4.animals.AnimalAdapter
import com.example.itmolab4.databinding.ActivityMainBinding
import com.example.itmolab4.ui.AnimalsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isBound = false
    private lateinit var boundServiceConnection: ServiceConnection
    private var animalsService: AnimalsService? = null

    // TODO: connect and setup AnimalsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animalsViewModel = AnimalsViewModel()

        binding.animalsRV.adapter = AnimalAdapter(animalsViewModel.animals.value)
        binding.animalsRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        boundServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                val binderBridge: AnimalsService.AnimalBinder =
                    p1 as AnimalsService.AnimalBinder
                animalsService = binderBridge.getAnimalsService()
                isBound = true
            }
            override fun onServiceDisconnected(p0: ComponentName?) {
                isBound = false
                animalsService = null
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                animalsViewModel.animals.collectLatest {
                    (binding.animalsRV.adapter as AnimalAdapter).updateAnimals(it)
                    if (animalsViewModel.progress == 100) {
                        binding.loadingProgressBar.visibility = View.GONE
                        val loadingFinished = System.currentTimeMillis()
                        val loadingTime =
                            (loadingFinished - animalsViewModel.loadingStarted).toDouble() / 1000
                        animalsService?.createNotificationFinished(
                            loadingTime.toFloat(),
                            animalsViewModel.loaded
                        )
                        if (isBound) {
                            AnimalsApp.instance.unbindService(boundServiceConnection)
                            isBound = false
                            animalsService = null
                        }
                    } else {
                        binding.loadingProgressBar.visibility = View.VISIBLE
                        binding.loadingProgressBar.progress = animalsViewModel.progress
                        animalsService?.updateNotificationLoading(animalsViewModel.progress)
                    }
                }
            }
        }

        binding.updateButton.setOnClickListener {
            initService()
            animalsViewModel.fetchAnimals(5)
        }
    }

    private fun initService() {
        if (!isBound) {
            val intent = Intent(AnimalsApp.instance, AnimalsService::class.java)
            AnimalsApp.instance.bindService(intent, boundServiceConnection, BIND_AUTO_CREATE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
    }

    override fun onStop() {
        AnimalsApp.instance.unbindService(boundServiceConnection)
        super.onStop()
    }
}