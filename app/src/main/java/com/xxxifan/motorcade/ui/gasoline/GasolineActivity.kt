package com.xxxifan.motorcade.ui.gasoline

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import org.jetbrains.anko.button
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout

class GasolineActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    verticalLayout {
      button("记录油耗") {
        setOnClickListener { startActivity<GasRecordActivity>() }
      }
      button("查看记录") {
        setOnClickListener { toast("再努力一点，就能看到了哦") }
      }
      gravity = Gravity.CENTER
    }
  }
}