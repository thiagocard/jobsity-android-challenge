package com.jobsity.android.challenge.ui.show_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jobsity.android.challenge.databinding.ItemGenreBinding

class GenresAdapter(
    private val genres: List<String>
) : RecyclerView.Adapter<GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount() = genres.size

}

class GenreViewHolder(private val binding: ItemGenreBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: String) {
        binding.genre = genre
    }

}
