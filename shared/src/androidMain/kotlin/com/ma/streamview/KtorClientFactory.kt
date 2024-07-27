package com.ma.streamview

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.ProxyBuilder
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.timeout
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.logging.Logger
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

actual class KtorClientFactory {
    actual fun build(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        coerceInputValues = true
                    })
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : io.ktor.client.plugins.logging.Logger {
                    override fun log(message: String) {
                        Log.v("HttpClient", message)
                    }
                }
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 60_000
                requestTimeoutMillis = 60_000
                socketTimeoutMillis = 60_000
            }
        }
    }
}
//            install(HttpTimeout) {
//                connectTimeoutMillis = 60_000
//                requestTimeoutMillis =  60_000
//                socketTimeoutMillis = 60_000
//            }
//install(HttpRequestRetry) {
//    maxRetries = 5
//    retryIf { request, response ->
//        !response.status.isSuccess()
//    }
//    retryOnExceptionIf { request, cause ->
//        cause is NetworkError
//    }
//    delayMillis { retry ->
//        retry * 3000L
//    } // retries in 3, 6, 9, etc. seconds
//}
// public var connectTimeoutMillis: Long?
//            get() = _connectTimeoutMillis
//            set(value) {
//                _connectTimeoutMillis = checkTimeoutValue(value)
//            }
//            defaultRequest {
//                url(BASE_URL)
//                headers {
//                    header(HttpHeaders.ContentType, ContentType.Application.Json)
//                }
//            }
