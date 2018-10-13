package com.xxxifan.motorcade.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.avos.avoscloud.AVFile
import com.avos.avoscloud.im.v2.AVIMException
import com.avos.avoscloud.im.v2.audio.AVIMAudioRecorder
import com.avos.avoscloud.im.v2.audio.AVIMAudioRecorder.RecordEventListener
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage
import com.xxxifan.motorcade.App
import com.xxxifan.motorcade.ChatManager
import com.xxxifan.motorcade.IntercomMessage
import com.xxxifan.motorcade.R
import com.xxxifan.motorcade.send
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_main.createGroupBtn
import kotlinx.android.synthetic.main.activity_main.sendBtn
import kotlinx.android.synthetic.main.activity_main.stateText
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.File


class MainActivity : AppCompatActivity() {
  companion object {
    private val audioPath = File(App.ctx.cacheDir, "media").apply { mkdirs() }
    private val outputFile = File(audioPath, "${System.currentTimeMillis()}.file")
  }

  // TODO: 2018/8/28 file cannot be same with multiple time usage.
  private val recorder = AVIMAudioRecorder(outputFile.path, RecordListener)
  @Volatile private var recoding = false

  @Subscribe
  fun onUpdateStateText(str: String) {
    stateText.append(str)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    EventBus.getDefault().register(this)
    startConnection()
    bindClicks()
  }

  private fun startConnection() {
    val id = if (Build.DEVICE == "generic_x86") "test1" else "xxxifan"
    stateText.text = "login with $id"
    ChatManager.startConnection(id) {
      createGroupBtn.isEnabled = true
      stateText.append(", connected")
    }
  }

  private fun bindClicks() {
    createGroupBtn.setOnClickListener {
      ChatManager.queryConversation { conv ->
        if (conv != null) {
          sendBtn.isEnabled = true
        } else {
          ChatManager.createNewConversation {
            sendBtn.isEnabled = true
          }
        }
      }
    }

    sendBtn.setOnClickListener { view ->
      if (view.tag == null) {
        AndPermission.with(this)
            .runtime()
            .permission(Permission.RECORD_AUDIO, Permission.WRITE_EXTERNAL_STORAGE)
            .onGranted {
              recordMessage()
            }
            .start()
        view.tag = "recording"
        (view as Button).text = "停止"
      } else {
        finishRecord()
        view.tag = null
        (view as Button).text = "发送"
      }
    }
  }

  private fun recordMessage() {
    recorder.start()
    recoding = true

    stateText.append(", Recording")
    IntercomMessage(part = IntercomMessage.START)
        .send(callback = object : AVIMConversationCallback() {
          override fun done(e: AVIMException?) {
            e?.printStackTrace() ?: kotlin.run {
              EventBus.getDefault().post(", send start")
            }
          }
        })
    Thread {
      while (recoding) {
        Thread.sleep(500)
        val length = outputFile.length()
        val file = AVFile.withFile("testaudio-$length.aac", outputFile)
        val im = AVIMAudioMessage(file).apply { text = "$length" }
        IntercomMessage(audio = im).send()
      }
    }.start()
  }

  private fun finishRecord() {
    recorder.stop()
    recoding = false
    stateText.append(", Sending")
  }


  override fun onDestroy() {
    super.onDestroy()
    ChatManager.close()
    EventBus.getDefault().unregister(this)
  }

  private object RecordListener : RecordEventListener {
    override fun onFinishedRecord(time: Long, msg: String?) {
      Handler().postDelayed({
        IntercomMessage(
            part = IntercomMessage.END).send(callback = object : AVIMConversationCallback() {
          override fun done(e: AVIMException?) {
            e?.printStackTrace() ?: kotlin.run {
              EventBus.getDefault().post(", done")
            }
          }
        })
      }, 500)
    }

    override fun onStartRecord() {
    }
  }
}
