package com.ma.streamview.data.preferences

actual fun PlatformContext.putInt(
    key: String,
    value: Int
) {
}

actual fun PlatformContext.putString(
    key: String,
    value: String
) {
}

actual fun PlatformContext.putBool(
    key: String,
    value: Boolean
) {
}

actual fun PlatformContext.getInt(
    key: String,
    default: Int
): Int {
    TODO("Not yet implemented")
}

actual fun PlatformContext.getString(key: String): String? {
    TODO("Not yet implemented")
}

actual fun PlatformContext.getBool(
    key: String,
    default: Boolean
): Boolean {
    TODO("Not yet implemented")
}