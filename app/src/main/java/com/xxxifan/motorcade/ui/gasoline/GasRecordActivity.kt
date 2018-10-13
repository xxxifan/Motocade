package com.xxxifan.motorcade.ui.gasoline

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.SHOW_AS_ACTION_IF_ROOM
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.amap.api.location.AMapLocationListener
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import com.xxxifan.motorcade.AmapHelper
import com.xxxifan.motorcade.R
import com.xxxifan.motorcade.request
import com.xxxifan.motorcade.ui.gasoline.model.GasRecord
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.gas_record.gasLabelText
import kotlinx.android.synthetic.main.gas_record.gasPriceText
import kotlinx.android.synthetic.main.gas_record.gasTimeText
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class GasRecordActivity : AppCompatActivity() {

  private val gasRecord: GasRecord = GasRecord()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.gas_record)

    initView()
  }

  private fun initView() {
    setGasTimeText(Calendar.getInstance().time)
    gasTimeText.setOnClickListener {
      generateTime { setGasTimeText(it) }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menu?.add(0, 1, 0, "提交")
        ?.setIcon(R.drawable.ic_check_black_24dp)
        ?.setShowAsAction(SHOW_AS_ACTION_IF_ROOM)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == 1) {
      // todo save
      toast("save")
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onResume() {
    super.onResume()
    if (gasRecord.province.isEmpty()) {
      request(*Permission.Group.LOCATION) { granted -> if (granted) getLocationGasPrice() }
    }
  }


  private fun generateTime(function: (time: Date) -> Unit) {
    DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth ->
      TimePickerDialog.newInstance({ view, hourOfDay, minute, second ->
        val date = Calendar.getInstance().apply {
          set(year, monthOfYear, dayOfMonth, hourOfDay, minute)
        }
        function(date.time)
      }, true).show(supportFragmentManager, "TimerPicker")
    }.show(supportFragmentManager, "DatePicker")
  }

  private fun setGasTimeText(date: Date) {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    gasTimeText.text = format.format(date.time)
    gasRecord.createTime = gasTimeText.text.toString()
  }

  private fun getLocationGasPrice() {
    AmapHelper.fix(0, AMapLocationListener {
      gasRecord.province = it.province
      if (it.province.isEmpty()) {
        gasLabelText.text = "未定位"
        gasLabelText.setOnClickListener { showOilSelector(null) }
      } else {
        PriceFetcher.get(gasRecord.province) { prices ->
          gasLabelText.text = "${gasRecord.province} #92"
          gasLabelText.setOnClickListener { showOilSelector(prices) }
        }
      }
    })
  }

  private fun showOilSelector(prices: List<String>?) {
    MaterialDialog(this@GasRecordActivity).show {
      title(text = "选择汽油标号")
      listItems(R.array.gasoline_types) { dialog, index, text ->
        gasRecord.gasType = text
        gasLabelText.text = "${gasRecord.province} $text"
        prices?.get(index)?.let {
          gasPriceText.setText(it)
          gasRecord.gasPrice = it
        }
      }
    }
  }
}