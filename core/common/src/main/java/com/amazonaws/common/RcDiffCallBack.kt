package com.amazonaws.common

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class RcDiffCallBack : DiffUtil.ItemCallback<RecyclerViewModelItem<*>>() {

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: RecyclerViewModelItem<*>,
        newItem: RecyclerViewModelItem<*>
    ): Boolean {
        return oldItem.getRealModelData() == newItem.getRealModelData()
    }

    override fun areItemsTheSame(
        oldItem: RecyclerViewModelItem<*>,
        newItem: RecyclerViewModelItem<*>
    ): Boolean {
        return oldItem.itemListSameWith(newItem)
    }

    override fun getChangePayload(
        oldItem: RecyclerViewModelItem<*>,
        newItem: RecyclerViewModelItem<*>
    ): Any? {
        return oldItem.itemListPayloadSameWith(newItem)
    }
}