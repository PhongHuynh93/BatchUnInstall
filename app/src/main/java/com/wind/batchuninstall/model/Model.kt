package com.wind.batchuninstall.model

import android.graphics.drawable.Drawable
import com.wind.batchuninstall.ListItemModel

/**
 * Created by Phong Huynh on 7/11/2020.
 */
data class AppInfo(
    val packageName: String,
    val appName: CharSequence,
    val icon: Drawable,
    val processName: String,
    val isSystemApp: Boolean
): ListItemModel()
