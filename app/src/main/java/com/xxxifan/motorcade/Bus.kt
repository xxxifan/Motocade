package com.xxxifan.motorcade

import java.util.Collections

object Bus {
  // [type, [obj1, obj2...]]
  val classTree = Collections.synchronizedMap(mapOf<Class<Any>, Array<Class<Any>>>())

  fun register(obj: Class<Any>, vararg type: Class<Any>) {
    type.forEach {
      val typeArray = classTree[it]
      if (typeArray?.contains(obj) == false) {
        typeArray.plus(obj)
      } else if (typeArray == null) {
        classTree[it] = arrayOf(obj)
      }
    }
  }
}