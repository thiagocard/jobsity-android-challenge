package com.jobsity.android.challenge.ui.shows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jobsity.android.challenge.BR
import com.jobsity.android.challenge.databinding.ItemShowAtListBinding
import com.jobsity.android.challenge.domain.model.ShowAtList

val diffCallback = object : DiffUtil.ItemCallback<ShowAtList>() {

    override fun areItemsTheSame(oldItem: ShowAtList, newItem: ShowAtList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShowAtList, newItem: ShowAtList): Boolean {
        return oldItem.name == newItem.name && oldItem.poster == newItem.poster
    }

}

class ShowsPagingDataAdapter(
    private val onItemClick: (ShowAtList) -> Unit,
    private val onItemMenuClick: ((View, ShowAtList) -> Unit)? = null,
) : PagingDataAdapter<ShowAtList, ShowAtListViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: ShowAtListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowAtListViewHolder {
        val binding =
            ItemShowAtListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowAtListViewHolder(binding, onItemClick, onItemMenuClick)
    }

}

class ShowAtListViewHolder(
    private val binding: ItemShowAtListBinding,
    private val onItemClick: (ShowAtList) -> Unit,
    private val onItemMenuClick: ((View, ShowAtList) -> Unit)? = null,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ShowAtList?) {
        item?.let {
            itemView.setOnClickListener { onItemClick(item) }
            binding.setVariable(BR.show, it)
            binding.executePendingBindings()
            val displayMenuOnItems = onItemMenuClick != null
            binding.ivMenu.isVisible = displayMenuOnItems
            if (displayMenuOnItems) {
                binding.ivMenu.setOnClickListener { v -> onItemMenuClick?.invoke(v, item) }
            }
        }
    }
}
