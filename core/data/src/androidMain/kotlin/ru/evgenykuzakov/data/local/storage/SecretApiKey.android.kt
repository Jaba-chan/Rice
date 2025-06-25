package ru.evgenykuzakov.data.local.storage

import ru.evgenykuzakov.rice.core.data.BuildConfig

actual object SecretApiKeys {
    actual fun getFirebaseApiKey(): String = BuildConfig.FIREBASE_API_KEY
}