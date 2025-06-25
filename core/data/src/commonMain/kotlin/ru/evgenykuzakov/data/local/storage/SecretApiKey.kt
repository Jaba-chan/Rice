package ru.evgenykuzakov.data.local.storage

expect object SecretApiKeys {
    fun getFirebaseApiKey(): String
}

fun provideFirebaseApiKey(): String {
    return SecretApiKeys.getFirebaseApiKey()
}