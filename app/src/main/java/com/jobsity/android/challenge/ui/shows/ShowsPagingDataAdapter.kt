package com.jobsity.android.challenge.ui.shows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.jobsity.android.challenge.databinding.ItemShowAtListBinding
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.ui.common.adapter.ShowAtListViewHolder

val diffCallback = object : DiffUtil.ItemCallback<ShowAtList>() {

    override fun areItemsTheSame(oldItem: ShowAtList, newItem: ShowAtList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShowAtList, newItem: ShowAtList): Boolean {
        return oldItem.name == newItem.name && oldItem.poster == newItem.poster
    }

}

class ShowsPagingDataAdapter(
    private val onItemClick: (ShowAtList) -> Unit
) : PagingDataAdapter<ShowAtList, ShowAtListViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: ShowAtListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowAtListViewHolder {
        val binding =
            ItemShowAtListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowAtListViewHolder(binding, onItemClick)
    }

}
