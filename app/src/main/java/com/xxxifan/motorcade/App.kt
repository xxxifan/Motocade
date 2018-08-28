package com.xxxifan.motorcade

import android.app.Application
import com.avos.avoscloud.AVOSCloud

class App : Application() {

  companion object {
    lateinit var ctx: App
  }

  override fun onCreate() {
    ctx = this
    super.onCreate()
    AVOSCloud.initialize(this,"vuv3qu9txf6037lfjv6cxrq2jk676x1eh65f56jg6dy3zwis","7d51rbc5arfor1dxejnl560riw1gir2s0a4d0cm7p5nojvno");
    AVOSCloud.setDebugLogEnabled(true)
  }
}