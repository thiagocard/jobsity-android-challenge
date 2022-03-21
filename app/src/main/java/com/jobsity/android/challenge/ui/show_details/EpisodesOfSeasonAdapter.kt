package com.jobsity.android.challenge.ui.show_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jobsity.android.challenge.BR
import com.jobsity.android.challenge.databinding.ItemEpisodeBinding
import com.jobsity.android.challenge.domain.model.EpisodeOfShow

class EpisodesOfSeasonAdapter(
    private val episodes: List<EpisodeOfShow>,
    private val onEpisodeClickListener: (EpisodeOfShow) -> Unit
) : RecyclerView.Adapter<SeasonEpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonEpisodeViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeasonEpisodeViewHolder(binding, onEpisodeClickListener)
    }

    override fun onBindViewHolder(holder: SeasonEpisodeViewHolder, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount() = episodes.size

}

class SeasonEpisodeViewHolder(
    private val binding: ItemEpisodeBinding,
    private val onEpisodeClickListener: (EpisodeOfShow) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(episodeOfShow: EpisodeOfShow) {
        itemView.setOnClickListener { onEpisodeClickListener(episodeOfShow) }
        binding.setVariable(BR.episode, episodeOfShow)
    }

}