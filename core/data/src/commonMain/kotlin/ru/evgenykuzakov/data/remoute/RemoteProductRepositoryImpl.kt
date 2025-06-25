package ru.evgenykuzakov.data.remoute

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import ru.evgenykuzakov.data.local.storage.SecretApiKeys
import ru.evgenykuzakov.data.mapper.toDomain
import ru.evgenykuzakov.data.remoute.model.ProductResponse
import ru.evgenykuzakov.domain.model.Meal
import ru.evgenykuzakov.domain.repository.RemoteProductRepository

class RemoteProductRepositoryImpl(
    private val client: HttpClient,
    private val apiKey: SecretApiKeys
) : RemoteProductRepository {

    override suspend fun searchProductByName(name: String): List<Meal> {
        val url = "https://firestore.googleapis.com/v1/projects" +
                "/YOUR_PROJECT_ID/databases/(default)/documents:runQuery?key=$apiKey"
        val response: ProductResponse = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(buildFirestoreQueryJson(name))
        }.body()
        return response.products.map { it.toDomain() }.take(10)
    }

    private fun buildFirestoreQueryJson(searchToken: String): JsonObject {
        return buildJsonObject {
            put("structuredQuery", buildJsonObject {
                put("from", buildJsonArray {
                    add(buildJsonObject {
                        put("collectionId", "products")
                    })
                })
                put("where", buildJsonObject {
                    put("fieldFilter", buildJsonObject {
                        put("field", buildJsonObject {
                            put("fieldPath", "nameTokens")
                        })
                        put("op", "ARRAY_CONTAINS")
                        put("value", buildJsonObject {
                            put("stringValue", searchToken)
                        })
                    })
                })
                put("limit", 10)
            })
        }
    }

}