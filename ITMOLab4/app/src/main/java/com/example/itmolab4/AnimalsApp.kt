package com.example.itmolab4

import android.app.Application
import com.example.itmolab4.api.AnimalsRetrofit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AnimalsApp: Application() {
    lateinit var retrofit: Retrofit
    lateinit var animals: AnimalsRetrofit

    override fun onCreate() {
        super.onCreate()
        instance = this

        val moshiBuilder = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        retrofit = Retrofit.Builder()
            .baseUrl("https://zoo-animal-api.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshiBuilder))
            .build()
        animals = retrofit.create(AnimalsRetrofit::class.java)
    }

    companion object {
        lateinit var instance: AnimalsApp
            private set
    }
}