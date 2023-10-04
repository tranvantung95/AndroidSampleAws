package com.amazonaws.common.databindingVer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amazonaws.common.BR
import java.util.concurrent.Executors

abstract class BaseRecyclerWithDiffAdapter<T : Any, V : ViewDataBinding>(
    callBack: DiffCallBack<T>
) : ListAdapter<T, BaseViewHolder<V>>(
    AsyncDifferConfig.Builder<T>(callBack)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {
    private val bindingVariableInteract: Int = BR.itemInteract
    private val bindingVariablePosition: Int = BR.position

    @get:LayoutRes
    abstract val layoutRes: Int

    private val viewPool = RecyclerView.RecycledViewPool()

    var setViewPool: (parentRootView: View, viewPool: RecyclerView.RecycledViewPool) -> Unit =
        { _, _ ->

        }

    protected open fun createBinding(parent: ViewGroup, viewType: Int): V {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutRes,
            parent,
            false
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        return BaseViewHolder(
            createBinding(
                parent,
                viewType
            ).apply {
                setViewPool(this.root, viewPool)
                setVariable(bindingVariableInteract, createItemInteract())
            }
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        try {
            holder.binding.apply {
                setVariable(bindingVariablePosition, position)
            }
            bind(holder.binding, getItem(position), position)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        holder.binding.executePendingBindings()
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<V>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            try {
                holder.binding.apply {
                    setVariable(bindingVariablePosition, position)
                }
                bind(holder.binding, getItem(position), position)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            holder.binding.executePendingBindings()
        } else {
            bindWithPayloads(holder.binding, getItem(position) ?: return, position, payloads)
        }
    }

    override fun submitList(list: List<T>?) {
        super.submitList(ArrayList<T>(list ?: listOf()))
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    abstract fun createItemInteract(): BaseRecyclerViewItemInteract<T>

    protected abstract fun bind(binding: V, item: T, position: Int)

    open fun bindWithPayloads(
        binding: V,
        item: T,
        position: Int,
        payloads: MutableList<Any>
    ) {

    }
}