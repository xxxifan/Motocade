package com.xxxifan.motorcade.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.READ_PHONE_STATE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xxxifan.devbox.core.util.Fragments
import com.xxxifan.devbox.core.ext.PERMISSION_REQUEST_CODE
import com.xxxifan.motorcade.R
import com.xxxifan.devbox.core.ext.request
import com.xxxifan.motorcade.ui.map.AmapFragment
import com.yanzhenjie.permission.AndPermission

class NewActivity : AppCompatActivity() {
  companion object {
    val PERMISSIONS = arrayOf(
        ACCESS_COARSE_LOCATION,
        ACCESS_FINE_LOCATION,
        WRITE_EXTERNAL_STORAGE,
        READ_PHONE_STATE
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new)

    request(*PERMISSIONS) {granted ->
      if (granted && savedInstanceState == null) {
        Fragments.checkout(this, AmapFragment()).into(R.id.new_container)
      } else if (!granted) {

      }
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == PERMISSION_REQUEST_CODE) {
      if (AndPermission.hasPermissions(this, *PERMISSIONS)) {

      }
    }
  }
}