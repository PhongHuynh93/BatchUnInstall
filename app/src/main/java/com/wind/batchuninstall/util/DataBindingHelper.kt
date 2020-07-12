package com.wind.batchuninstall.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.wind.batchuninstall.model.AppInfo
import com.wind.batchuninstall.view.UninstallAppPagerAdapter
import com.wind.batchuninstall.viewmodel.AppType

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


@BindingAdapter("pageItems")
fun setViewPagerData(viewPager: ViewPager2, data: Map<AppType, List<AppInfo>>) {
    (viewPager.adapter as? UninstallAppPagerAdapter)?.setData(data)
}

