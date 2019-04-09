package com.xxxifan.motorcade.ui.map

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode
import com.amap.api.maps.MapView
import com.xxxifan.motorcade.ui.map.AmapContract.View
import com.amap.api.maps.model.MyLocationStyle
import com.xxxifan.motorcade.App
import java.text.SimpleDateFormat
import java.util.Date


class AmapPresenter(val mapView: MapView, lifecycle: Lifecycle,
    override var view: View? = null) : AmapContract.Presenter {

  private lateinit var amapCLient:AMapLocationClient

  init {
    lifecycle.addObserver(this)
  }

  override fun onLifeCyclePaused(owner: LifecycleOwner) {
    mapView.onPause()
    amapCLient.stopLocation()
  }

  override fun onLifeCycleResumed(owner: LifecycleOwner) {
    mapView.onResume()
    amapCLient.startLocation()
    getCarsNearby()
  }

  override fun startLocation() {
    amapCLient = AMapLocationClient(App.ctx)
    val clientOption = AMapLocationClientOption().apply {
      locationMode = AMapLocationMode.Hight_Accuracy
      interval = 2000
    }
    amapCLient.setLocationOption(clientOption)
    amapCLient.setLocationListener { amapLocation ->
      if (amapLocation != null) {
        if (amapLocation.errorCode == 0) {
          //定位成功回调信息，设置相关消息
          amapLocation.locationType//获取当前定位结果来源，如网络定位结果，详见定位类型表
          amapLocation.latitude//获取纬度
          amapLocation.longitude//获取经度
          amapLocation.accuracy//获取精度信息
          amapLocation.time//定位时间
        } else {
          //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
        }
      }
    }
    amapCLient.startLocation()
  }

  private fun getCarsNearby() {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onLifeCycleDestroyed(owner: LifecycleOwner) {
    mapView.onDestroy()
  }

  override fun enableLocation() {
    val locationStyle = MyLocationStyle().apply {
      myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER)
      interval(2000)
    }
    mapView.map.apply {
      myLocationStyle = locationStyle
      uiSettings.isMyLocationButtonEnabled = true
      isMyLocationEnabled = true
    }
  }

}