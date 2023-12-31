package com.raveline.ourrelationsapp.ui.data.state

open class StateEvent<out T> (private val content:T){
    var hasBeenHandled = false
        private set

    fun getContentOrNull():T? {
        return  if (hasBeenHandled) {
            null
        }else{
            hasBeenHandled = true
            content
        }
    }
}