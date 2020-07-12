package com.wind.batchuninstall

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * Created by Phong Huynh on 7/11/2020.
 * https://android.jlelse.eu/using-databinding-like-a-pro-to-write-generic-recyclerview-adapter-f94cb39b65c4
 */
class GenericAdapter<T : ListItemModel>(@LayoutRes val layoutId: Int) :
    RecyclerView.Adapter<GenericAdapter.GenericViewHolder<T>>() {

    private val items = mutableListOf<T>()
    private var inflater: LayoutInflater? = null
    private var onListItemClickListener: OnListItemClickListener<T>? = null

    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnListItemClickListener(onListItemClickListener: OnListItemClickListener<T>){
        this.onListItemClickListener = onListItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val layoutInflater = inflater ?: LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        return GenericViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        val itemViewModel = items[position]
        itemViewModel.adapterPosition = position
        onListItemClickListener?.let { itemViewModel.onListItemClickListener = it }
        holder.bind(itemViewModel)
    }


    class GenericViewHolder<T : ListItemModel>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemViewModel: T) {
            binding.setVariable(BR.item, itemViewModel)
            binding.executePendingBindings()
        }

    }

    interface OnListItemClickListener<in T: ListItemModel> {
        fun onClick(view: View, position: Int, item: T)
    }
}

abstract class ListItemModel {
    var adapterPosition: Int = -1
    var onListItemClickListener: GenericAdapter.OnListItemClickListener<*>? = null
}


@BindingAdapter("items")
fun bindAdapter(recyclerView: RecyclerView, data: List<ListItemModel>?) {
    data?.let {
        val adapter = recyclerView.adapter
        (adapter as? GenericAdapter<ListItemModel>)?.setItems(it)
    }
}
