package com.xxxifan.motorcade

import android.preference.PreferenceManager

val sharedPreference
  get() = PreferenceManager.getDefaultSharedPreferences(App.ctx)!!