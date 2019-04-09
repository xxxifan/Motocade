package com.xxxifan.devbox.core.ext

import android.app.Application
import android.preference.PreferenceManager
import com.xxxifan.motorcade.App
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val PERMISSION_REQUEST_CODE = 9999

val sharedPreference
  get() = PreferenceManager.getDefaultSharedPreferences(App.ctx)!!

fun Application.install(block: suspend () -> Unit) {
  GlobalScope.launch { block() }
}