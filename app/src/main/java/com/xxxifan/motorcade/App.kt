package com.xxxifan.motorcade

import android.app.Application
import com.avos.avoscloud.AVOSCloud
import com.avos.avoscloud.im.v2.AVIMClient
import com.avos.avoscloud.im.v2.AVIMConversation
import com.avos.avoscloud.im.v2.AVIMMessageManager
import com.avos.avoscloud.im.v2.MessageHandler


class App : Application() {

  companion object {
    lateinit var ctx: App
  }

  override fun onCreate() {
    ctx = this
    super.onCreate()
    AVOSCloud.initialize(this, "vuv3qu9txf6037lfjv6cxrq2jk676x1eh65f56jg6dy3zwis",
        "7d51rbc5arfor1dxejnl560riw1gir2s0a4d0cm7p5nojvno");
    AVOSCloud.setDebugLogEnabled(true)
    AVIMMessageManager.registerAVIMMessageType(IntercomMessage::class.java)
    AVIMMessageManager.registerMessageHandler(IntercomMessage::class.java, IntercomMessageHandler())
  }

  class IntercomMessageHandler : MessageHandler<IntercomMessage>() {
    override fun onMessage(message: IntercomMessage, conv: AVIMConversation, client: AVIMClient) {
      when (message.part) {
        0 -> {
          // TODO: 2018/8/28 save file
        }
        IntercomMessage.START -> {
          // TODO: 2018/8/28 start
        }
        IntercomMessage.END -> {
          // TODO: 2018/8/28 end
        }
      }
    }

    override fun onMessageReceipt(p0: IntercomMessage?, p1: AVIMConversation?, p2: AVIMClient?) {
    }

  }
}