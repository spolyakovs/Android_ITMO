package com.example.itmolab4.api

import com.example.itmolab4.animals.Animal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimalsRetrofit {
    @GET("animals/rand/{number}")
    fun getAnimals(
        @Path("number")
        number: Int
    ): Call<List<Animal>>
}