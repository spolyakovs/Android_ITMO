package com.example.itmolab3.cards

import com.example.itmolab3.R

data class TinderCard(val id: Int, var avatar_src: Int, var name: String, var description: String) {
    var expanded = false
}

val testUserlist = listOf(
    TinderCard(1, R.drawable.shrek, "Shrek", "Some shrek description short"),
    TinderCard(2, R.drawable.fiona, "Fiona", "Some fiona description very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very long "),
)