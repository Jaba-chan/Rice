package ru.evgenykuzakov.data.local.storage

expect object SecretApiKeys {
    fun getFirebaseApiKey(): String
}

fun provideFirebaseApiKey(): SecretApiKeys {
    return SecretApiKeys
}