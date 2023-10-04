package com.amazonaws.common.databindingVer

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<out T : ViewDataBinding>(val binding: T) :
    RecyclerView.ViewHolder(binding.root)

interface BaseRecyclerViewItemInteract<I> {
    fun onRootClick(item: I, position: Int)
}

interface SampleItemInteract : BaseRecyclerViewItemInteract<String>

