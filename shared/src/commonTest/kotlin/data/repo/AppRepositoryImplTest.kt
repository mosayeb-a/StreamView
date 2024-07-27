package data.repo


import com.ma.streamview.common.formatRelativeDate
import kotlin.test.Test
import kotlin.test.assertEquals

class AppRepositoryImplTest {

    @Test
    fun `buildUrl with specific parameters should generate correct URL`() {
        val date = "2024-07-03T16:51:06Z"
        val name = formatRelativeDate(date)
        assertEquals("yesterday", name)
    }


}