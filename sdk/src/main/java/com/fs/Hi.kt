package com.fs

import android.app.Activity
import android.app.Application
import com.fs.core.Advantage

object Hi {

    fun init(app: Application, key: String, flag: String) {
        Advantage.init(app,key, flag)
    }

    fun init(activity: Activity) {
        Advantage.activityInit(activity)
    }
}