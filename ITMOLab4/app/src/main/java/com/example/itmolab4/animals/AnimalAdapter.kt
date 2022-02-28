package com.example.itmolab4.animals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.itmolab4.databinding.AnimalsItemBinding
import kotlinx.coroutines.flow.StateFlow

class AnimalAdapter(
    private var animals: List<Animal>
) : RecyclerView.Adapter<AnimalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        var viewBinding = AnimalsItemBinding.inflate(LayoutInflater.from(parent.context))
        viewBinding.root.layoutParams = RecyclerView.LayoutParams(parent.measuredWidth, parent.measuredWidth)
        return AnimalViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) =
        holder.bind(animals[position])

    override fun getItemCount(): Int = animals.size

    fun updateAnimals(newAnimals: List<Animal>) {
        val diffResult = DiffUtil.calculateDiff(
            DiffUtilCallback(animals, newAnimals)
        )
        animals = newAnimals
        diffResult.dispatchUpdatesTo(this)
    }

    private class DiffUtilCallback(
        private val oldList: List<Animal>,
        private val newList: List<Animal>
    ): DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    }

}