package com.ma.streamview

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.utils.io.errors.IOException

suspend inline fun <reified T> HttpClient.exceptionAwareRequest(
    url: String,
    block: HttpRequestBuilder.() -> Unit = {},
): T {
    val result = try {
        request(urlString = url) {
            block()
        }
    } catch (e: IOException) {
        throw StreamException(
            type = StreamException.Type.SIMPLE,
            userFriendlyMessage = "unavailable service",
        )
    }

    when (result.status.value) {
        in 200..299 -> Unit
        in 400..499 -> throw StreamException(userFriendlyMessage = "bad reqeust")
        500 -> throw StreamException(userFriendlyMessage = "server error")
        else -> throw StreamException(userFriendlyMessage = "unknown error")
    }

    return try {
        result.body<T>()
    } catch (e: Exception) {
        println("mylog: ktorExctension error is: $e ")
        e.printStackTrace()
        throw StreamException(
            type = StreamException.Type.EMPTY_SCREEN,
            userFriendlyMessage = "Unknown Error",
        )
    }
}