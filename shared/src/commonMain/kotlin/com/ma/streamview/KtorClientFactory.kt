package com.ma.streamview

import io.ktor.client.HttpClient

expect class KtorClientFactory() {
    fun build() : HttpClient
}