package com.xxxifan.motorcade

import android.app.Activity
import android.preference.PreferenceManager
import com.yanzhenjie.permission.AndPermission

val sharedPreference
  get() = PreferenceManager.getDefaultSharedPreferences(App.ctx)!!

fun Activity.request(vararg permissions: String, callback: (granted: Boolean) -> Unit) {
  AndPermission.with(this)
      .runtime()
      .permission(permissions)
      .onGranted {
        callback(true)
      }
      .onDenied {
        if (AndPermission.hasAlwaysDeniedPermission(this, it)) {
          // 这里使用一个Dialog展示没有这些权限应用程序无法继续运行，询问用户是否去设置中授权。
          AndPermission.with(this)
              .runtime()
              .setting()
              .onComeback {
                callback(true)
              }
              .start()
        } else {
          callback(false)
        }
      }
      .rationale { context, data, executor ->
        executor.execute()
      }
      .start()
}