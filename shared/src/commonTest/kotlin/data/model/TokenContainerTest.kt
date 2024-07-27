package data.model

import com.ma.streamview.data.model.TokenContainer
import com.ma.streamview.data.model.TokenResponse
import kotlinx.datetime.*
import kotlin.test.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class TokenContainerTest {

    @BeforeTest
    fun setup() {
        // Reset TokenContainer before each test
        TokenContainer.update(null)
    }

    @Test
    fun testTokenUpdateAndGet() {
        val currentTime = Clock.System.now()
        val tokenResponse = TokenResponse("sample_access_token", 3600) // 1 hour
        TokenContainer.update(tokenResponse)

        // Assert the access token is set correctly
        assertEquals("sample_access_token", TokenContainer.getToken(currentTime))

        // Simulate advancing time by 1 hour
        val oneHourLater = currentTime.plus(3600.seconds)

        // Assert the token has expired
        assertNull(TokenContainer.getToken(oneHourLater))
    }

    @Test
    fun testTokenExpiration() {
        val currentTime = Clock.System.now()
        val tokenResponse = TokenResponse("sample_access_token",1800) // 30 minutes
        TokenContainer.update(tokenResponse, currentTime)

        // Assert the access token is set correctly
        assertEquals("sample_access_token", TokenContainer.getToken(currentTime))

        // Simulate advancing time by 31 minutes
        val thirtyOneMinutesLater = currentTime.plus(31.minutes)

        // Assert the token has expired
        assertNull(TokenContainer.getToken(thirtyOneMinutesLater))
    }

    @Test
    fun testNullToken() {
        TokenContainer.update(null)

        // Assert the access token is null
        assertNull(TokenContainer.getToken(Clock.System.now()))
    }
}

// Mock TokenResponse class

// Modified TokenContainer to handle time-based token expiration
