package com.example.itmolab4.animals

data class Animal (val id: Int, val name: String, val image_link: String)

val testAnimalsList = listOf(
    Animal(189, "White-Faced Saki", "https://upload.wikimedia.org/wikipedia/commons/b/b3/White-faced_Saki_2008-07.jpg"),
    Animal(23, "Bald Eagle", "https://upload.wikimedia.org/wikipedia/commons/1/1a/About_to_Launch_%2826075320352%29.jpg")
)