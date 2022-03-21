package com.jobsity.android.challenge.ui.shows_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jobsity.android.challenge.databinding.ItemShowAtListBinding
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.ui.common.adapter.ShowAtListViewHolder

class ShowsSearchAdapter(
    private val shows: List<ShowAtList>,
    private val onItemClick: (ShowAtList) -> Unit
) : RecyclerView.Adapter<ShowAtListViewHolder>() {

    override fun onBindViewHolder(holder: ShowAtListViewHolder, position: Int) {
        holder.bind(shows[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowAtListViewHolder {
        val binding =
            ItemShowAtListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowAtListViewHolder(binding, onItemClick)
    }

    override fun getItemCount() = shows.size

}
