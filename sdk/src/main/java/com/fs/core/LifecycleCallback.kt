package com.fs.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

object LifecycleCallback :Application.ActivityLifecycleCallbacks{

    var activity:WeakReference<Activity>?=null

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
    }

    override fun onActivityStarted(p0: Activity) {

    }

    override fun onActivityResumed(p0: Activity) {
        activity=WeakReference(p0)
    }

    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityDestroyed(p0: Activity) {

    }
}