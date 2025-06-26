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
import ru.evgenykuzakov.data.mapper.toDomain
import ru.evgenykuzakov.data.mapper.toProductDto
import ru.evgenykuzakov.data.remoute.model.RunQueryResponse
import ru.evgenykuzakov.domain.model.Product
import ru.evgenykuzakov.domain.repository.RemoteProductRepository

class RemoteProductRepositoryImpl(
    private val client: HttpClient,
    private val apiKey: String
) : RemoteProductRepository {

    override suspend fun searchProductByName(name: String): List<Product> {
        val url = "https://firestore.googleapis.com/v1/projects/rice-70d39/databases/(default)/documents:runQuery?key=$apiKey"
        val response:List<RunQueryResponse> = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(buildFirestoreQueryJson(name))
        }.body()
        return response.mapNotNull { it.document }.map { it.toProductDto().toDomain() }
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