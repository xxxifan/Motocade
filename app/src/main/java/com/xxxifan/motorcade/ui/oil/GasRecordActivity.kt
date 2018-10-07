package com.xxxifan.motorcade.ui.oil

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amap.api.location.AMapLocationListener
import com.xxxifan.motorcade.AmapHelper
import com.xxxifan.motorcade.R
import com.xxxifan.motorcade.request
import com.yanzhenjie.permission.Permission

class GasRecordActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.gas_record)
  }

  override fun onResume() {
    super.onResume()
    request(*Permission.Group.LOCATION) {granted ->
      if (granted) {
        AmapHelper.fix(0, AMapLocationListener {
          it.city
        })
      }
    }
  }
}