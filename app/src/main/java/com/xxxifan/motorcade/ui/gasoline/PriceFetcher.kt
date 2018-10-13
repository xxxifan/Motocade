package com.xxxifan.motorcade.ui.gasoline

import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import org.jsoup.Jsoup

object PriceFetcher {
  fun get(province: String, block: (List<String>?) -> Unit) {
    GlobalScope.launch {
      try {
//        val doc = Jsoup.parse(App.ctx.resources.openRawResource(R.raw.youjia), "utf-8",
//            "http://youjia.chemcp.com/")
        val doc = Jsoup.connect("http://youjia.chemcp.com/").get()
        val priceTr = doc.select("div.cpbaojia table tbody")[0].select("tr")
        val item = priceTr.select("tr").find { province.contains(it.child(0).text()) }
        if (item != null) {
          val texts = item.select("td").map { it.text() }.toList()
          launch(Dispatchers.Main) {
            block(texts.subList(2, texts.size - 1)) // #92, #95, #0
          }
        } else {
          launch(Dispatchers.Main) {
            block(null)
          }
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
}