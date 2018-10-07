package com.xxxifan.motorcade

import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode
import com.amap.api.location.AMapLocationListener

object AmapHelper {
  private val locationClient: AMapLocationClient by lazy {
    AMapLocationClient(App.ctx)
  }

  fun fix(interval: Long, listener: AMapLocationListener) {
    val options = AMapLocationClientOption()
    options.locationMode = AMapLocationMode.Hight_Accuracy
    if (interval > 0) {
      options.isOnceLocation = true
    } else {
      options.interval = interval
    }
    locationClient.setLocationOption(options)
    locationClient.setLocationListener(listener)
    locationClient.startLocation()
  }
}