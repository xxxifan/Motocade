package com.xxxifan.motorcade

import com.avos.avoscloud.im.v2.AVIMMessageField
import com.avos.avoscloud.im.v2.AVIMMessageType
import com.avos.avoscloud.im.v2.AVIMTypedMessage
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage

@AVIMMessageType(type=100) class IntercomMessage(audio: AVIMAudioMessage? = null, part: Int = 0) : AVIMTypedMessage() {
  @AVIMMessageField(name = "audio") var audio: AVIMAudioMessage? = null
  @AVIMMessageField(name = "part") var part: Int = 0

  init {
    this.audio = audio
    this.part = part
  }

  companion object {
    const val START = 1
    const val END = -1
  }
}