package com.kaloglu.actualflyer.util

import com.zhuinden.eventemitter.EventEmitter

class SimpleEventEmitter : EventEmitter<Unit>() {
    fun emit() = emit(Unit)
}