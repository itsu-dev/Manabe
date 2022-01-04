package dev.itsu.manabe.ui.web

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import dev.itsu.manabe.R
import dev.itsu.manabe.api.Manaba
import dev.itsu.manabe.ifNotNull
import kotlinx.coroutines.*
import java.nio.charset.StandardCharsets

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        this.intent.getStringExtra("url") ifNotNull {
            GlobalScope.launch(context = Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    val news = Manaba.getNews(intent.getStringExtra("url")!!)?.textHtml ?: "null"
                    this@WebActivity.resources.openRawResource(R.raw.template).bufferedReader(StandardCharsets.UTF_8).use {
                        it.readText()
                    }.replace("@__CONTENTS__@", news)

                }.let {
                    findViewById<WebView>(R.id.web_view).apply {
                        //this.webViewClient = WebViewClient()
                        this.settings.javaScriptEnabled = true
                        this.loadDataWithBaseURL(null, it, "text/html; charset=utf-8", "UTF-8", null)
                    }
                }
            }
            it
        }
    }
}