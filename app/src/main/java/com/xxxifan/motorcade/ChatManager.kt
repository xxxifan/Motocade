package com.xxxifan.motorcade

import android.os.Build
import com.avos.avoscloud.im.v2.AVIMClient
import com.avos.avoscloud.im.v2.AVIMConversation
import com.avos.avoscloud.im.v2.AVIMException
import com.avos.avoscloud.im.v2.AVIMMessage
import com.avos.avoscloud.im.v2.AVIMMessageOption
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback

object ChatManager {
  private var client: AVIMClient? = null

  var currentConversation: AVIMConversation? = null

  fun startConnection(id: String, block: () -> Unit) {
    client = AVIMClient.getInstance(id)
    client!!.open(object : AVIMClientCallback() {
      override fun done(client: AVIMClient?, e: AVIMException?) {
        assert(e == null)
        block()
      }
    })
  }

  fun queryConversation(block: (AVIMConversation?) -> Unit) {
    client?.conversationsQuery
        ?.whereEqualTo("name", "Team 1")
        ?.findInBackground(object : AVIMConversationQueryCallback() {
          override fun done(list: MutableList<AVIMConversation>?, e: AVIMException?) {
            e?.printStackTrace() ?: kotlin.run {
              if (list?.isEmpty() == false) {
                currentConversation = list[0]
                block(currentConversation)
              } else {
                block(null)
              }
            }
          }
        })
  }

  fun createNewConversation(block: (AVIMConversation) -> Unit) {
    val friend = if (Build.DEVICE == "generic_x86") "xxxifan" else "test1"

    client?.createConversation(arrayListOf(friend), "Team 1", null,
        object : AVIMConversationCreatedCallback() {
          override fun done(avimConversation: AVIMConversation?, e: AVIMException?) {
            e?.printStackTrace() ?: kotlin.run {
              currentConversation = avimConversation
              block(avimConversation!!)
            }
          }
        })
  }

  fun close() {
    client?.close(null)
    client = null
  }
}

fun AVIMMessage.send(options: AVIMMessageOption? = null,
    callback: AVIMConversationCallback? = null) {
  ChatManager.currentConversation?.sendMessage(this, options, callback)
}