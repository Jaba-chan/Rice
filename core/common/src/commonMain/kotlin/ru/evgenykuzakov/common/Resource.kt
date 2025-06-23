package ru.evgenykuzakov.common


sealed class Resource<out T>{
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val message: String?) :  Resource<T>()
    class Loading<T> : Resource<T>()
}