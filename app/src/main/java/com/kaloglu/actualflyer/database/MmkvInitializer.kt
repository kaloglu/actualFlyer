package com.kaloglu.actualflyer.database

import android.content.Context
import androidx.startup.Initializer
import com.tencent.mmkv.MMKV

class MmkvInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        MMKV.initialize(context)
    }

    override fun dependencies() = emptyList<Class<out Initializer<*>>>()
}