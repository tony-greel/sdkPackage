package com.fs.gpshell

import android.app.Application
import com.fs.Hi

class ShellApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Hi.init(this,"5NsxcwKWfzwzQCskxpHahd","1000414")
    }
}