package com.wind.batchuninstall.model

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Parcelable
import com.wind.batchuninstall.ListItemModel
import kotlinx.android.parcel.Parcelize

/**
 * Created by Phong Huynh on 7/11/2020.
 */
@Parcelize
data class AppInfo(
    val packageName: String,
    val appName: CharSequence,
    val processName: String,
    val isSystemApp: Boolean
): ListItemModel(), Parcelable {
    fun getIcon(packageManager: PackageManager): Drawable = packageManager.getApplicationIcon(packageName)
}
