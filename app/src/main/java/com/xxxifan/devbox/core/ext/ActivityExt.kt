package com.xxxifan.devbox.core.ext

import android.app.Activity
import com.afollestad.materialdialogs.MaterialDialog
import com.xxxifan.motorcade.R.string
import com.yanzhenjie.permission.AndPermission

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
              .start(PERMISSION_REQUEST_CODE)
        } else {
          callback(false)
        }
      }
      .rationale { context, data, executor ->
        executor.execute()
      }
      .start()
}

fun Activity.permissionDialog() {
  MaterialDialog(this).title(string.title_alert).message(string.msg_permissions)
}