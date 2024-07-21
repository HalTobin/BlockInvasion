package org.chapeaumoineau.blockinvasion

import android.app.Application
import di.KoinInitializer

class BlockInvasionApp: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}