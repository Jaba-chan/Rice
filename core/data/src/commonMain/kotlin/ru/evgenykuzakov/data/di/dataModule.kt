package ru.evgenykuzakov.data.di

import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.evgenykuzakov.data.local.LocalUserMealRepositoryImpl
import ru.evgenykuzakov.data.local.storage.provideFirebaseApiKey
import ru.evgenykuzakov.data.remoute.RemoteProductRepositoryImpl
import ru.evgenykuzakov.domain.repository.LocalUserMealRepository
import ru.evgenykuzakov.domain.repository.RemoteProductRepository
import ru.evgenykuzakov.network.di.networkModule

val dataModule = module {
    single(named("firebaseApiKey")) { provideFirebaseApiKey() }

    single {
        LocalUserMealRepositoryImpl(get())
    }.bind<LocalUserMealRepository>()

    single {
        RemoteProductRepositoryImpl(
            client = get<HttpClient>(),
            apiKey = get<String>(named("firebaseApiKey"))
        )
    }.bind<RemoteProductRepository>()

    includes(networkModule)
}
