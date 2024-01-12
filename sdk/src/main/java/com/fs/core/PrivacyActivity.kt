package com.fs.core

import android.app.Activity
import android.os.Bundle

class PrivacyActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Advantage.initView(this)

    }
}