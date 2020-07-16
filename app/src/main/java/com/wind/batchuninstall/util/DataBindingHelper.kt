package com.wind.batchuninstall.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.wind.batchuninstall.GenericAdapter
import com.wind.batchuninstall.ListItemModel
import com.wind.batchuninstall.model.AppInfo
import com.wind.batchuninstall.view.UninstallAppAdapter
import com.wind.batchuninstall.view.UninstallAppPagerAdapter

/**
 * Created by Phong Huynh on 7/12/2020.
 */
@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).into(imageView)
}

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: Drawable) {
    Glide.with(imageView.context).load(url).into(imageView)
}

@BindingAdapter("items")
fun bindAdapter(recyclerView: RecyclerView, data: List<AppInfo>?) {
    data?.let {
        val adapter = recyclerView.adapter
        (adapter as? UninstallAppAdapter)?.setData(it)
    }
}

