package com.fs.gpshell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.TextView
import com.fs.Hi
import com.fs.core.Advantage
import com.fs.core.JsBridge
import com.fs.gpshell.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Hi.init(this)


//        val webView=WebView(this)
//        val setting: WebSettings = webView.settings
//        setting.javaScriptEnabled = true
//        setting.javaScriptCanOpenWindowsAutomatically = true
//        setting.setSupportMultipleWindows(true)
//        setting.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
//        setting.domStorageEnabled = true
//        setting.cacheMode = WebSettings.LOAD_DEFAULT
//        setting.allowContentAccess = true
//        setting.databaseEnabled = true
//        setting.setGeolocationEnabled(true)
//        setting.useWideViewPort = true
//        setting.setSupportZoom(false)
//        webView.webChromeClient = WebChromeClient()
//
//        webView.addJavascriptInterface(JsBridge(this), "jsThirdBridge")
//        setting.javaScriptCanOpenWindowsAutomatically = true
//        webView.loadUrl("https://www.afun.games/?ch=1000414")
//         setContentView(webView)


    }
}