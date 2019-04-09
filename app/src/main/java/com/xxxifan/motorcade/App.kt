package com.xxxifan.motorcade

import android.app.Application
import com.avos.avoscloud.AVOSCloud
import com.xxxifan.devbox.core.ext.install
import com.xxxifan.motorcade.App.Components.leancloud
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class App : Application() {

  companion object {
    lateinit var ctx: App
  }

  override fun onCreate() {
    ctx = this
    super.onCreate()
    install(leancloud)
  }

  object Components {
    val leancloud = suspend {
      withContext(Dispatchers.Main) {
        AVOSCloud.initialize(ctx, "vuv3qu9txf6037lfjv6cxrq2jk676x1eh65f56jg6dy3zwis",
            "7d51rbc5arfor1dxejnl560riw1gir2s0a4d0cm7p5nojvno")
        AVOSCloud.setDebugLogEnabled(true)
      }
    }
  }
}