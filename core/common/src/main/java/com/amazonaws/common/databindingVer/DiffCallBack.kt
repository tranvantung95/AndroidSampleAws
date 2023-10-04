package com.amazonaws.common.databindingVer

import androidx.recyclerview.widget.DiffUtil

open class DiffCallBack<T : Any>(open val compare: (T, T) -> Boolean) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return compare(oldItem, newItem)
    }
}

//open class DiffCallBackWithPayload<T>(
//    val sameContent: (T, T) -> Boolean,
//    val itemTheSame: (T, T) -> Boolean,
//    private val changePayload: ((T, T) -> Any?)? = null
//) : DiffCallBack<T>(sameContent) {
//    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
//        return itemTheSame(oldItem, newItem)
//    }
//
//    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
//        return sameContent(oldItem, newItem)
//    }
//
//    override fun getChangePayload(oldItem: T, newItem: T): Any? {
//        return changePayload?.invoke(oldItem, newItem)
//    }
//}