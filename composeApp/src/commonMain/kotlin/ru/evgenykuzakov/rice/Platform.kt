package ru.evgenykuzakov.rice

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform