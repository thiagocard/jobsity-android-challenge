package com.jobsity.android.challenge.ui.show_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jobsity.android.challenge.BR
import com.jobsity.android.challenge.databinding.ItemEpisodeBinding
import com.jobsity.android.challenge.databinding.ItemEpisodeSeasonHeaderBinding
import com.jobsity.android.challenge.domain.model.EpisodeOfShow
import com.jobsity.android.challenge.domain.model.EpisodeOrderedBySeason

class EpisodesOfShowAdapter(
    private val episodes: List<EpisodeOrderedBySeason>,
    private val itemClickListener: (EpisodeOfShow) -> Unit
) : RecyclerView.Adapter<EpisodeOrderedBySeasonViewHolder<EpisodeOrderedBySeason>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodeOrderedBySeasonViewHolder<EpisodeOrderedBySeason> {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding =
                    ItemEpisodeSeasonHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                EpisodeSeasonHeaderViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EpisodeOfShowViewHolder(binding, itemClickListener)
            }
        }
    }

    override fun onBindViewHolder(
        holder: EpisodeOrderedBySeasonViewHolder<EpisodeOrderedBySeason>,
        position: Int
    ) {
        holder.bind(episodes[position])
    }

    override fun getItemCount() = episodes.size

    override fun getItemViewType(position: Int): Int {
        val item = episodes[position]
        return if (item.episode != null)
            super.getItemViewType(position)
        else VIEW_TYPE_HEADER
    }

    companion object {
        const val VIEW_TYPE_HEADER = 1
    }

}

abstract class EpisodeOrderedBySeasonViewHolder<T>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun bind(type: T)
}

class EpisodeOfShowViewHolder(
    private val binding: ItemEpisodeBinding,
    private val itemClickListener: (EpisodeOfShow) -> Unit
) : EpisodeOrderedBySeasonViewHolder<EpisodeOrderedBySeason>(binding.root) {

    override fun bind(type: EpisodeOrderedBySeason) {
        type.episode?.let {
            itemView.setOnClickListener { itemClickListener(type.episode) }
            binding.setVariable(BR.episode, it)
        }
    }

}

class EpisodeSeasonHeaderViewHolder(
    private val binding: ItemEpisodeSeasonHeaderBinding
) : EpisodeOrderedBySeasonViewHolder<EpisodeOrderedBySeason>(binding.root) {

    override fun bind(type: EpisodeOrderedBySeason) {
        type.seasonHeader?.let {
            binding.setVariable(BR.season, it)
        }
    }

}
