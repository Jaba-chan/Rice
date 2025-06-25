package ru.evgenykuzakov.network.di

import io.ktor.client.HttpClient
import org.koin.dsl.module
import ru.evgenykuzakov.network.provideHttpClient

val networkModule = module {
    single<HttpClient> { provideHttpClient() }
}