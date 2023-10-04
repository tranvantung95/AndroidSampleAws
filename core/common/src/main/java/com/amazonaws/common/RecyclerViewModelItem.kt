package com.amazonaws.common

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

interface RecyclerViewModelItem<DATA> {

    fun getRealModelData(): DATA

    @LayoutRes
    fun itemListGetViewType(): Int

    fun bindItemDataToRecyclerView(binding: ViewBinding)

    fun itemListSameWith(otherItem: RecyclerViewModelItem<*>): Boolean

    fun itemListPayloadSameWith(otherItem: RecyclerViewModelItem<*>): Boolean?

    fun getDefaultSpanCount(): Int? = null

    fun onHolderAttach(holder: RecyclerViewHolder, parentRecyclerView: RecyclerView) {}

    fun onHolderDetach(holder: RecyclerViewHolder) {}
}