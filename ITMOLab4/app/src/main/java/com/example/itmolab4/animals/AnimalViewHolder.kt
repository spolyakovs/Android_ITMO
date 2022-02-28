package com.example.itmolab4.animals

import androidx.recyclerview.widget.RecyclerView
import com.example.itmolab4.R
import com.example.itmolab4.databinding.AnimalsItemBinding
import com.squareup.picasso.Picasso

class AnimalViewHolder(private val binding: AnimalsItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(animal: Animal) {
        Picasso.get()
            .load(animal.image_link)
            .placeholder(R.drawable.ic_default_icon)
            .into(binding.animalImage)
        binding.animalTitle.text = animal.name
    }
}