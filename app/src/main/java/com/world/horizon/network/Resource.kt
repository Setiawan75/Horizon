package com.world.horizon.network

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val throwable: String) : Resource<T>()
}