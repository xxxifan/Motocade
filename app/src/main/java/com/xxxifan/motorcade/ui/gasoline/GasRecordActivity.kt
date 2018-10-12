package com.xxxifan.motorcade.ui.gasoline

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amap.api.location.AMapLocationListener
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog
import com.xxxifan.motorcade.AmapHelper
import com.xxxifan.motorcade.R
import com.xxxifan.motorcade.request
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.gas_record.gasTimeText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class GasRecordActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.gas_record)

    initView()
  }

  private fun initView() {
    setGasTimeText(Calendar.getInstance().time)
    AmapHelper.fix(0, AMapLocationListener { aLocation ->
      println(aLocation.city)
    })
    gasTimeText.setOnClickListener {
      val now = Calendar.getInstance()
      DatePickerDialog.Builder(
          { dialog, year, monthOfYear, dayOfMonth ->
            GridTimePickerDialog.Builder({ viewGroup, hourOfDay, minute ->
              val date = Calendar.getInstance().apply {
                set(year, monthOfYear, dayOfMonth, hourOfDay, minute)
              }
              setGasTimeText(date.time)
            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true
            ).build().show(supportFragmentManager, "TimerPicker")
          }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
      ).build().show(supportFragmentManager, "DatePiker")

    }
  }

  private fun setGasTimeText(date: Date) {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    gasTimeText.text = format.format(date.time)
  }

  override fun onResume() {
    super.onResume()
    request(*Permission.Group.LOCATION) { granted ->
      if (granted) {
        AmapHelper.fix(0, AMapLocationListener {
          it.city
        })
      }
    }
  }
}