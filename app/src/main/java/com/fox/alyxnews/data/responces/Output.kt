package com.fox.alyxnews.data.responces

sealed class Output<out T : Any> {
    data class Success<out T : Any>(val output: T) : Output<T>()
    data class Loading(val loading: Boolean = false): Output<Boolean>()
    data class Error(val exception: Exception) : Output<Nothing>()
}