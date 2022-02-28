package com.example.itmolab4.ui

import androidx.lifecycle.ViewModel
import com.example.itmolab4.AnimalsApp
import com.example.itmolab4.animals.Animal
import com.example.itmolab4.animals.testAnimalsList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.await

class AnimalsViewModel: ViewModel() {
    private val _animals = MutableStateFlow<List<Animal>>(listOf())
    val animals = _animals.asStateFlow()

    private var currentJob: Job? = null

    var progress: Int = 100
    var loaded: Int = 0
    var loadingStarted: Long = 0

    fun fetchAnimals(number: Int) {
        currentJob?.cancel()
        loadingStarted = System.currentTimeMillis()
        progress = 0
        loaded = 0
        _animals.value = listOf()

        currentJob = CoroutineScope(Dispatchers.Default).launch {
            for (i in 1..number) {
                delay(1000L)
                val call = AnimalsApp.instance.animals.getAnimals(2)
                val result = call.await()
                loaded += result.size
                progress = i * 100 / number
                _animals.value = _animals.value + result
            }
        }
    }
}