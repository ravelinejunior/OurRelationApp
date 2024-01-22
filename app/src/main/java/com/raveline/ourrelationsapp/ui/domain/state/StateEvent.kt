package com.raveline.ourrelationsapp.ui.domain.state

open class StateEvent<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContentOrNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}
