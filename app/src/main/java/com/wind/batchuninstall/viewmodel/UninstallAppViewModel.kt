package com.wind.batchuninstall.viewmodel

import android.content.Context
import android.content.pm.PackageManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wind.batchuninstall.model.AppInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*

/**
 * Created by Phong Huynh on 7/11/2020.
 */
class UninstallAppViewModel @ViewModelInject constructor(@ApplicationContext private val applicationContext: Context, private val packageManager: PackageManager):
    ViewModel() {

    private val _appInfoList = MutableLiveData<List<AppInfo>>()
    val appInfoList = _appInfoList

    init {
        getInstalledApps()
    }

    fun getInstalledApps() {
        viewModelScope.launch {
            _appInfoList.value = withContext(Dispatchers.IO) {
                val pkgs = applicationContext.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                val appInfoList = pkgs.map {
                    val packageName = it.packageName
                    AppInfo(
                        packageName,
                        packageManager.getApplicationLabel(it),
                        packageManager.getApplicationIcon(packageName),
                        it.processName
                    )
                }
                appInfoList
            }
        }

    }
}