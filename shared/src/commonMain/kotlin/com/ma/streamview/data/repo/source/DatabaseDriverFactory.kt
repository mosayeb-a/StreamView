package com.ma.streamview.data.repo.source

import com.squareup.sqldelight.db.SqlDriver
import com.ma.streamview.database.StreamDatabase


expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}


class StreamDatabaseFactory(
    private val driver: DatabaseDriverFactory
) {
    fun createDatabase(): StreamDatabase {
        return StreamDatabase(
            driver = driver.create()
        )
    }
}

