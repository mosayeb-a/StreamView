package com.ma.streamview.data.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.seconds

//object TokenContainer {
//    private var token: TokenResponse? = null
//    private var expirationTime: Instant? = null
//
//    fun update(newToken: TokenResponse?, currentTime: Instant = Clock.System.now()) {
//        token = newToken
//        expirationTime = newToken?.let { currentTime.plus(it.expiresIn.seconds) }
//    }
//
//    fun getToken(currentTime: Instant = Clock.System.now()): String? {
//        return if (currentTime < (expirationTime ?: currentTime)) {
//            token?.accessToken
//        } else {
//            null
//        }
//    }
//}

//object TokenContainer {
//    private const val DEFAULT_EXPIRATION_TIME = -1L
//    private var token: String? = null
//    private var expirationTime: Instant = Instant.fromEpochSeconds(DEFAULT_EXPIRATION_TIME)
//
//    fun update(tokenResponse: TokenResponse?) {
//        if (tokenResponse != null) {
//            token = tokenResponse.accessToken
//            expirationTime = Clock.System.now().plus(tokenResponse.expiresIn.seconds)
//            println(
//                "helixRequest.TokenContainer. Access Token updated -> ${
//                    token?.substring(
//                        0,
//                        10
//                    )
//                }"
//            )
//            println("helixRequest.TokenContainer. Token expiration time -> $expirationTime")
//        } else {
//            token = null
//            expirationTime = Instant.fromEpochSeconds(DEFAULT_EXPIRATION_TIME)
//        }
//    }
//
//    fun setToken(token: String) {
//        this.token = token
//    }
//
//    fun getToken(): String? {
//        println("helixRequest in TokenContainer before. token: $token ")
//        val currentTime = Clock.System.now()
//        println("helixRequest. Current time -> $currentTime")
//        println("helixRequest. Expiration time -> $expirationTime")
//
//        if (token != null && currentTime > expirationTime) {
//            println("helixRequest in TokenContainer before. token is null")
//            token = null
//        }
//
//        println("helixRequest in TokenContainer after. token: $token ")
//        return token
//    }
//}
object TokenContainer {
    var token: String? = null
        private set

    fun update(token: String?) {
        println("Access Token-> ${token?.substring(0, 10)}")
        TokenContainer.token = token
    }
}
//
//object TokenContainer {
//    private const val DEFAULT_EXPIRATION_TIME = -1L // Default expiration time for no token or invalid expiresIn value
//    private var accessToken: String? = null
//    private var expirationTime: Instant = Instant.fromEpochSeconds(DEFAULT_EXPIRATION_TIME)
//
//    fun update(tokenResponse: TokenResponse?) {
//        if (tokenResponse != null) {
//            accessToken = tokenResponse.accessToken
//            expirationTime = Clock.System.now().plus(tokenResponse.expiresIn.toLong())
//            println("Access Token -> ${accessToken?.substring(0, 10)}")
//        } else {
//            accessToken = null
//            expirationTime = Instant.fromEpochSeconds(DEFAULT_EXPIRATION_TIME)
//        }
//    }
//
//    fun getToken(): String? {
//        val currentTime = Clock.System.now()
//        if (accessToken != null && currentTime >= expirationTime) {
//            accessToken = null // Nullify token if it has expired
//        }
//        return accessToken
//    }
//}
//        expirationTime = Clock.System.now().plus(tokenResponse.expiresIn.seconds)
//    fun getToken(): String? {
//        val currentTime = Clock.System.now()
//        if (accessToken != null && currentTime >= expirationTime) {
//            accessToken = null // Nullify token if it has expired
//        }
//        return accessToken
//    }
//}