package com.wind.batchuninstall.viewmodel

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.wind.batchuninstall.model.AppInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*

/**
 * Created by Phong Huynh on 7/11/2020.
 */
sealed class AppType
object SystemAppType : AppType()
object NormalAppType : AppType()
class UninstallAppViewModel @ViewModelInject constructor(@ApplicationContext private val applicationContext: Context, private val packageManager: PackageManager):
    ViewModel() {

    private val _appInfoMap = MutableLiveData<MutableMap<AppType, MutableList<AppInfo>>>()
    val appInfoMap: LiveData<Map<AppType, List<AppInfo>>> = _appInfoMap.map {
        it.toMap()
    }

    init {
        getInstalledApps()
    }

    fun getInstalledApps() {
        viewModelScope.launch {
            _appInfoMap.value = withContext(Dispatchers.IO) {
                val pkgs = applicationContext.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                val appInfoList = pkgs.map {
                    val packageName = it.packageName
                    AppInfo(
                        packageName,
                        packageManager.getApplicationLabel(it),
                        packageManager.getApplicationIcon(packageName),
                        it.processName,
                        it.flags and ApplicationInfo.FLAG_SYSTEM != 0
                    )
                }
                val appBySystemMap = mutableMapOf<AppType, MutableList<AppInfo>>()
                for (app in appInfoList) {
                    if (app.isSystemApp) {
                        if (appBySystemMap.containsKey(SystemAppType)) {
                            val listSystemApp = mutableListOf<AppInfo>()
                            listSystemApp.add(app)
                            appBySystemMap[SystemAppType] = listSystemApp
                        } else {
                            appBySystemMap[SystemAppType]!!.add(app)
                        }
                    } else {

                    }
                }
                appBySystemMap
            }
        }

    }
}