package com.ma.streamview

import io.ktor.serialization.JsonConvertException
import io.ktor.utils.io.errors.IOException


class StreamExceptionMapper {
    companion object {
        fun map(exception: Exception): StreamException {
            when (exception) {
                is IOException -> {
                    return StreamException(
                        type = StreamException.Type.SIMPLE,
                        userFriendlyMessage = "unavailable service",
                    )
                }
                is  JsonConvertException->{
                    return StreamException(
                        id = 1,
                        type = StreamException.Type.SIMPLE,
                        userFriendlyMessage = "json convert exception",
                    )
                }
            }

            return StreamException(
                type = StreamException.Type.SIMPLE,
                userFriendlyMessage = "Unknown Error",
            )
        }
    }
}


class StreamException(val type: Type = Type.SIMPLE, val userFriendlyMessage: String,val id: Int? =null) : Throwable() {
    enum class Type {
        SIMPLE, EMPTY_SCREEN
    }
}