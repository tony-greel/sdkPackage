package com.fs.core

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.ArrayMap
import android.util.Log
import android.webkit.JavascriptInterface
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import org.json.JSONObject


class JsBridge(private val activity: Activity) {

    @JavascriptInterface
    fun openAppBrowser(url: String?) {
        activity.runOnUiThread {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            activity.startActivity(intent)
        }
    }

    @JavascriptInterface
    fun getSdkData(): String {
        val jsonData = JSONObject()
        jsonData.put(
            "af_id",
            AppsFlyerLib.getInstance().getAppsFlyerUID(activity.applicationContext)
        )
        jsonData.put("af_dev_key", Advantage.afKey)
        jsonData.put("af_bundleIdentifier", activity.packageName)
        return jsonData.toString()
    }

    @JavascriptInterface
    fun appsFlyerEvent(data: String) {

        val eventParameters = ArrayMap<String, Any>()
        val jsonData = JSONObject(data)
        var eventType = ""

        for (key in jsonData.keys()) {
            if (key == "event_type") {
                eventType = jsonData[key].toString()
            }
            eventParameters[key] = jsonData[key].toString()
        }

        if (eventType.isNotEmpty()) {

            AppsFlyerLib.getInstance()
                .logEvent(activity.applicationContext, eventType, eventParameters)
        }
    }


}