package com.fs.core.utils

import android.app.Activity
import android.content.Context

object SpUitl {


    private const val TOGGLE = "toggle"
    fun setToggle(activity: Activity, value: Boolean) {
        activity.getSharedPreferences("sf", Context.MODE_PRIVATE).edit().putBoolean(TOGGLE, value)
            .apply()
    }

    fun getToggle(activity: Activity) =
        activity.getSharedPreferences("sf", Context.MODE_PRIVATE).getBoolean(TOGGLE, false)

}