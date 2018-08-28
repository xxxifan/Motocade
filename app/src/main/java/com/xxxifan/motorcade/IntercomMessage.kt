package com.xxxifan.motorcade

import com.avos.avoscloud.im.v2.AVIMTypedMessage
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage

class IntercomMessage(val audio: AVIMAudioMessage? = null, val part: Int) : AVIMTypedMessage() {
  companion object {
    const val START = 1
    const val END = -1
  }
}