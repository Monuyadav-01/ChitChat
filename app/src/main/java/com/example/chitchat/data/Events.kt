package com.example.chitchat.data

open class Events<out T>(val content: T) {

    var hasBeenHandled = false
    fun getContentOrNull(): T? {
        return if (hasBeenHandled) null
        else {
            hasBeenHandled = true
            content
        }
    }


}