package com.ma.streamview.data.preferences

actual fun PlatformContext.putInt(key: String, value: Int) {
    getSharedPreferencesEditor().putInt(key, value).apply()
}

actual fun PlatformContext.getInt(key: String, default: Int): Int {
    return  getSharedPreferences().getInt(key, default )
}

actual fun PlatformContext.putString(key: String, value: String) {
    getSharedPreferencesEditor().putString(key, value).apply()
}

actual fun PlatformContext.getString(key: String): String? {
    return  getSharedPreferences().getString(key, null)
}

actual fun PlatformContext.putBool(key: String, value: Boolean) {
    getSharedPreferencesEditor().putBoolean(key, value).apply()
}

actual fun PlatformContext.getBool(key: String, default: Boolean): Boolean {
    return getSharedPreferences().getBoolean(key, default)
}

private fun PlatformContext.getSharedPreferences() = getSharedPreferences("stream_app", 0)

private fun PlatformContext.getSharedPreferencesEditor() = getSharedPreferences().edit()