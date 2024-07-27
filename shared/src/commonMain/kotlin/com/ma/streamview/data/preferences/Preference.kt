package com.ma.streamview.data.preferences

expect fun PlatformContext.putInt(key: String, value: Int)

expect fun PlatformContext.putString(key: String, value: String)

expect fun PlatformContext.putBool(key: String, value: Boolean)

expect fun PlatformContext.getInt(key: String, default: Int): Int

expect fun PlatformContext.getString(key: String) : String?

expect fun PlatformContext.getBool(key: String, default: Boolean): Boolean