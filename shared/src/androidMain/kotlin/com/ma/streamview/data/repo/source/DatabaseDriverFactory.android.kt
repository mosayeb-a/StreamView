package com.ma.streamview.data.repo.source

import android.content.Context
import com.ma.streamview.database.StreamDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver =
        AndroidSqliteDriver(StreamDatabase.Schema, context, "stream.db")
}