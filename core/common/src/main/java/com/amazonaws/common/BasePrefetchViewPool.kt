package com.amazonaws.common

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import tech.okcredit.layout_inflator.OkLayoutInflater
import java.util.LinkedList

abstract class BasePrefetchViewPool {

    private var isAdapterDetached = false

    abstract fun bindingInflater(inflatedView: View, @LayoutRes viewType: Int): ViewBinding

    private var prefetchViewList = HashMap<Int, Int>()

    // If the available item's count in pool < this value ( = MaxCount / 2) -> Auto create new one
    private var prefetchViewListDangerousLimit = HashMap<Int, Int>()
    private var prefetchItemBindingMap = HashMap<Int, LinkedList<ViewBinding>>()
    private var prefetchNestedChildViewList = HashMap<Int, Pair<Int, Int>>()
    private var currentParentView: ViewGroup? = null

    fun setAdapterActiveState(isActive: Boolean) {
        isAdapterDetached = isActive.not()
    }

    fun setPrefetchedViewList(viewTypeList: HashMap<Int, Int>) {
        prefetchViewList.clear()
        prefetchViewList.putAll(viewTypeList)
        viewTypeList.forEach { (layoutId, maxCountPreLoad) ->
            prefetchViewListDangerousLimit[layoutId] = maxCountPreLoad / 2
        }
    }

    fun prefetchItemBinding(parent: ViewGroup) {
        this.currentParentView = parent
        if (prefetchViewList.isEmpty()) return

        prefetchViewList.forEach { viewToCount ->
            if (isAdapterDetached) return
            initAsyncItemBinding(parent, viewToCount.key, viewToCount.value)
        }
    }

    fun getPrefetchedItemBinding(layoutId: Int): ViewBinding? {
        val binding = prefetchItemBindingMap[layoutId]?.poll()
        val currentPrefetchViewForThisLayout = prefetchItemBindingMap[layoutId]
        prefetchViewListDangerousLimit[layoutId]?.let { dangerousCountNeedToAddMoreToPool ->
            if (currentPrefetchViewForThisLayout == null || currentPrefetchViewForThisLayout.size < dangerousCountNeedToAddMoreToPool) {
                currentParentView?.let { prefetchItemBinding(it) }
            }
        }
        return binding
    }

    private fun prefetchChildItemBinding(parentViewBinding: ViewBinding, parentLayoutId: Int) {
        if (isAdapterDetached) return
        if (prefetchItemBindingMap[parentLayoutId] == null) return
        if (prefetchNestedChildViewList.isEmpty()) return
        val viewTypeToCount = prefetchNestedChildViewList[parentLayoutId] ?: return
        if (prefetchItemBindingMap[viewTypeToCount.first] != null) return

        initAsyncItemBinding(
            parentViewBinding.root as ViewGroup,
            viewTypeToCount.first,
            viewTypeToCount.second
        )
    }

    private fun initAsyncItemBinding(parent: ViewGroup, layoutId: Int, count: Int) {

        val okLayoutInflater = OkLayoutInflater(parent.context)

        for (index in 0 until count) {
            if (isAdapterDetached) return
            val currentCountForLayout = prefetchItemBindingMap[layoutId]?.size ?: 0
            if (currentCountForLayout >= count) return

            okLayoutInflater.inflate(layoutId, parent) { inflatedView ->
                run {

                    val viewBindingList = prefetchItemBindingMap[layoutId] ?: LinkedList()
                    if (viewBindingList.size < count) {
                        val itemViewBinding = bindingInflater(inflatedView, layoutId)
                        viewBindingList.add(itemViewBinding)
                    }
                    prefetchItemBindingMap[layoutId] = viewBindingList
                }
            }
        }

    }
}