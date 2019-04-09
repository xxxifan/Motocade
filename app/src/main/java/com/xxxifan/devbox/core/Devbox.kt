package com.xxxifan.devbox.core

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Process
import java.lang.NullPointerException

@SuppressLint("StaticFieldLeak")
/**
 * Created by xifan on 17-9-2.
 */
object Devbox {
  var appDelegate: Context? = null

  @JvmStatic fun init(context: Context) {
    this.appDelegate = context
  }

  /**
   * Get app package info.
   */
  @Throws(PackageManager.NameNotFoundException::class)
  @JvmStatic fun getPackageInfo(): PackageInfo {
    val manager = appDelegate?.packageManager ?: throw NullPointerException()
    return manager.getPackageInfo(appDelegate!!.packageName, 0)
  }

  @Suppress("DEPRECATION")
  @JvmStatic fun getVersionCode(): Long {
    return try {
      getPackageInfo().versionCode.toLong()
    } catch (e: Exception) {
      e.printStackTrace()
      0
    }
  }

  @JvmStatic fun getVersionName(): String {
    return try {
      getPackageInfo().versionName
    } catch (e: Exception) {
      e.printStackTrace()
      ""
    }
  }

  @JvmStatic fun isMainProcess(): Boolean {
    val am = appDelegate?.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        ?: return false
    val processes = am.runningAppProcesses
    val mainProcessName = appDelegate!!.packageName
    val myPid = Process.myPid()
    return processes.any { it.pid == myPid && mainProcessName == it.processName }
  }
}