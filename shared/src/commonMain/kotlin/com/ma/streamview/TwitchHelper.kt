package com.ma.streamview

object TwitchHelper {
    fun getTemplateUrl(url: String?, type: String): String? {
        if ((url == null)||(url == "")||(url.startsWith("https://vod-secure.twitch.tv/_404/404_processing")))
            return when (type) {
                "game" -> "https://static-cdn.jtvnw.net/ttv-static/404_boxart.jpg"
                "video" -> "https://vod-secure.twitch.tv/_404/404_processing_420x180.png"
                else -> null
            }
        val width = when (type) {
            "game" -> "285"
            "video" -> "1280"
            "profileimage" -> "300"
            else -> "" }
        val height = when (type) {
            "game" -> "380"
            "video" -> "720"
            "profileimage" -> "300"
            else -> "" }
        val reg1 = """-\d\d\dx\d\d\d""".toRegex()
        val reg2 = """\d\d\d\dx\d\d\d""".toRegex()
        val reg3 = """\d\d\dx\d\d\d""".toRegex()
        val reg4 = """\d\dx\d\d\d""".toRegex()
        val reg5 = """\d\d\dx\d\d""".toRegex()
        val reg6 = """\d\dx\d\d""".toRegex()
        if (type == "clip") return if (reg1.containsMatchIn(url)) reg1.replace(url, "") else url
        return when {
            url.contains("%{width}", true) -> url.replace("%{width}", width).replace("%{height}", height)
            url.contains("{width}", true) -> url.replace("{width}", width).replace("{height}", height)
            reg2.containsMatchIn(url) -> reg2.replace(url, "${width}x${height}")
            reg3.containsMatchIn(url) -> reg3.replace(url, "${width}x${height}")
            reg4.containsMatchIn(url) -> reg4.replace(url, "${width}x${height}")
            reg5.containsMatchIn(url) -> reg5.replace(url, "${width}x${height}")
            reg6.containsMatchIn(url) -> reg6.replace(url, "${width}x${height}")
            else -> url
        }
    }
    fun getVideoUrlMapFromPreviewHelix(url: String, type: String?): MutableMap<String, String> {
        val parts = url.split("/")
        val partAfterFourthSlash = if (parts.size > 4) {
            parts[4]
        } else {
            ""
        }

        val newBaseUrl = "https:///$partAfterFourthSlash.cloudfront.net/"
        val modifiedUrl = if (parts.size > 4) {
            newBaseUrl + parts.drop(5).joinToString("/") { it }
        } else {
            url
        }

        val baseFileName = modifiedUrl.substringAfterLast("/").substringBefore("-")
        val suffix =
            if (type?.lowercase() == "highlight") "highlight-$baseFileName.m3u8" else "index-dvr.m3u8"

        val qualityList = listOf(
            "chunked",
            "720p60",
            "720p30",
            "480p30",
            "360p30",
            "160p30",
            "144p30",
            "high",
            "medium",
            "low",
            "mobile",
            "audio_only"
        )

        val map = mutableMapOf<String, String>()
        qualityList.forEach { quality ->
            val qualityUrl = modifiedUrl
                .replace("//", "/")
                .replace("thumb", quality)
                .replaceAfterLast("/", suffix)
            map[quality] = qualityUrl
        }
        return map
    }
    fun getVideoUrlMapFromPreviewGQL(url: String, type: String?): MutableMap<String, String> {
        val qualityList = listOf("chunked", "720p60", "720p30", "480p30", "360p30", "160p30", "144p30", "high", "medium", "low", "mobile", "audio_only")
        val map = mutableMapOf<String, String>()
        qualityList.forEach { quality ->
            map[quality] = url
                .replace("storyboards", quality)
                .replaceAfterLast("/",
                    if (type?.lowercase() == "highlight") {
                        println("playbackservice service: mylog: type url: $type")
                        "highlight-${url.substringAfterLast("/").substringBefore("-")}.m3u8"
                    } else {
                        "index-dvr.m3u8"
                    }
                )
        }
        return map
    }
    //enum class VideoPeriodEnum(val value: String) {
    //    DAY("day"),
    //    WEEK("week"),
    //    MONTH("month"),
    //    ALL("all")
    //}
    //
    //enum class BroadcastTypeEnum(val value: String) {
    //    ALL("all"),
    //    ARCHIVE("archive"),
    //    HIGHLIGHT("highlight"),
    //    UPLOAD("upload")
    //}
    //
    //enum class VideoSortEnum(val value: String) {
    //    TIME("time"),
    //    VIEWS("views")
    //}
}
