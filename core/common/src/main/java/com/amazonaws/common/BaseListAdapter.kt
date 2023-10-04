package com.amazonaws.common

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.IndexOutOfBoundsException

abstract class BaseListAdapter :
    ListAdapter<RecyclerViewModelItem<*>, RecyclerViewHolder>(RcDiffCallBack()) {

    private lateinit var parentRc: RecyclerView

    private val prefetchViewPool = object : BasePrefetchViewPool() {
        override fun bindingInflater(inflatedView: View, @LayoutRes viewType: Int): ViewBinding =
            bindingInflatedViewInPool(inflatedView, viewType)
    }

    /**
     * Backup. Use when can not get binding from prefetch view pool
     */
    abstract fun inflateNewViewFromXml(parent: ViewGroup, @LayoutRes viewType: Int): ViewBinding

    /**
     * @param inflatedView: View inflated at BG thread by Prefetch ViewPool
     */
    abstract fun bindingInflatedViewInPool(
        inflatedView: View,
        @LayoutRes viewType: Int
    ): ViewBinding

    abstract fun layoutResWithPredictNumberOnScreen(): List<Pair<Int, Int>>

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        parentRc = recyclerView
        val prefetchItemMap = HashMap<Int, Int>()

        layoutResWithPredictNumberOnScreen().forEach {
            prefetchItemMap[it.first] = it.second * 5
        }
        prefetchViewPool.setAdapterActiveState(true)
        prefetchViewPool.setPrefetchedViewList(prefetchItemMap)
        // Start Background inflate for ViewPool
        prefetchViewPool.prefetchItemBinding(parentRc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val viewBindingFromPool = prefetchViewPool.getPrefetchedItemBinding(viewType)
        if (viewBindingFromPool != null) {
            val holder = RecyclerViewHolder(viewBindingFromPool)
            onSetItemClickAction(holder, viewType)
            return holder
        }

        val holder = RecyclerViewHolder(inflateNewViewFromXml(parent, viewType))
        onSetItemClickAction(holder, viewType)
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = this@BaseListAdapter.getItem(position) ?: return
        item.bindItemDataToRecyclerView(holder.binding)
    }

    override fun getItemViewType(position: Int): Int {
        val item = this@BaseListAdapter.getItem(position) ?: return 0
        val viewType = item.itemListGetViewType()
        if (isViewTypeValid(viewType)) return viewType

        throw Exception("ViewType is not valid. ViewType must be res layoutId to use prefetch pool")
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        prefetchViewPool.setAdapterActiveState(false)
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: RecyclerViewHolder) {
        super.onViewAttachedToWindow(holder)
        (getCurrentWrapperItem(holder) as? RecyclerViewModelItem<*>)?.onHolderAttach(holder, parentRc)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerViewHolder) {
        (getCurrentWrapperItem(holder) as? RecyclerViewModelItem<*>)?.onHolderDetach(holder)
        super.onViewDetachedFromWindow(holder)
    }

    open fun onSetItemClickAction(holder: RecyclerViewHolder, @LayoutRes viewType: Int) {}

    fun getCurrentRealDataItem(holder: RecyclerViewHolder): Any? {
        return try {
            currentList[holder.adapterPosition].getRealModelData()
        } catch (ex: IndexOutOfBoundsException) {
            null
        }
    }

    private fun getCurrentWrapperItem(holder: RecyclerViewHolder): Any? {
        return try {
            currentList[holder.adapterPosition]
        } catch (ex: IndexOutOfBoundsException) {
            null
        }
    }

    /**
     * View type must be layout id to work with prefetch view pool
     * Ex: R.layout.item_
     */
    private fun isViewTypeValid(viewType: Int): Boolean {
        val listLayoutResForThisAdapter = layoutResWithPredictNumberOnScreen().map { it.first }
        return listLayoutResForThisAdapter.contains(viewType)
    }

}