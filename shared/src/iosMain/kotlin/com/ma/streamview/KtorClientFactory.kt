package com.ma.streamview

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json


actual class KtorClientFactory {
    actual fun build(): HttpClient {
        return HttpClient(Darwin) {
            install(ContentNegotiation) {
                json()
            }
        }
    }
}
