package com.jobsity.android.challenge.ui.show_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.jobsity.android.challenge.BR
import com.jobsity.android.challenge.databinding.ItemSeasonBinding
import com.jobsity.android.challenge.domain.model.EpisodeOfShow
import com.jobsity.android.challenge.domain.model.Season

class SeasonsAdapter(
    private val seasons: List<Season>,
    private val onEpisodeClickListener: (EpisodeOfShow) -> Unit
) : RecyclerView.Adapter<SeasonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val binding = ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeasonViewHolder(binding, onEpisodeClickListener)
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        holder.bind(seasons[position])
    }

    override fun getItemCount() = seasons.size

}

class SeasonViewHolder(
    private val binding: ItemSeasonBinding,
    private val onEpisodeClickListener: (EpisodeOfShow) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(season: Season) {
        binding.setVariable(BR.season, season.number)
        binding.tvSeason.setOnClickListener {
            if (!binding.rvSeasonEpisodes.isVisible) {
                binding.rvSeasonEpisodes.isVisible = true
                binding.rvSeasonEpisodes.adapter =
                    EpisodesOfSeasonAdapter(season.episodes, onEpisodeClickListener)
            } else {
                binding.rvSeasonEpisodes.isVisible = false
                binding.rvSeasonEpisodes.adapter = null
            }

        }
    }

}
