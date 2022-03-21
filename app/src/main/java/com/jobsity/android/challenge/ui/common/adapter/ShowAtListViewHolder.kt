package com.jobsity.android.challenge.ui.common.adapter

import androidx.recyclerview.widget.RecyclerView
import com.jobsity.android.challenge.BR
import com.jobsity.android.challenge.databinding.ItemShowAtListBinding
import com.jobsity.android.challenge.domain.model.ShowAtList

class ShowAtListViewHolder(
    private val binding: ItemShowAtListBinding,
    private val onItemClick: (ShowAtList) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ShowAtList?) {
        item?.let {
            itemView.setOnClickListener { onItemClick(item) }
            binding.setVariable(BR.show, it)
            binding.executePendingBindings()
        }
    }
}