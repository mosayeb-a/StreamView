
package com.ma.streamview.android.feature.home.snap_to_center_behaviour

import android.util.Log

private const val DebugLog = false

internal object SnapperLog {
    inline fun d(tag: String = "SnapperFlingBehavior", message: () -> String) {
        if (DebugLog) {
            Log.d(tag, message())
        }
    }
}
