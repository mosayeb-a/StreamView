package data.repo

import com.ma.streamview.data.model.TokenContainer
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class RepositoryTest {

    private lateinit var repository: Repository
    private lateinit var localDataSource: LocalDataSource
    private lateinit var remoteDataSource: RemoteDataSource

    @BeforeTest
    fun setup() {
        localDataSource = mockk()
        remoteDataSource = mockk()
        repository = Repository(localDataSource, remoteDataSource)

        // Clear TokenContainer before each test
        TokenContainer.update(null)
    }

    @Test
    fun `isUserAuthenticated returns true when token is already present`() = runBlockingTest {
        // Arrange
        TokenContainer.update(TokenResponse("sample_access_token", 3600))

        // Act
        val result = repository.isUserAuthenticated()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `isUserAuthenticated returns true when token is loaded from local data source`() = runBlockingTest {
        // Arrange
        coEvery { localDataSource.loadToken() } just Runs
        coEvery { localDataSource.getToken() } returns TokenResponse("sample_access_token", 3600)

        // Act
        val result = repository.isUserAuthenticated()

        // Assert
        assertTrue(result)
        coVerify { localDataSource.loadToken() }
    }

    @Test
    fun `isUserAuthenticated returns true when token is fetched from remote data source`() = runBlockingTest {
        // Arrange
        coEvery { localDataSource.loadToken() } just Runs
        coEvery { localDataSource.getToken() } returns null
        coEvery { remoteDataSource.getAccessToken() } returns TokenResponse("sample_access_token", 3600)
        coEvery { localDataSource.saveToken(any()) } just Runs

        // Act
        val result = repository.isUserAuthenticated()

        // Assert
        assertTrue(result)
        coVerify { localDataSource.loadToken() }
        coVerify { remoteDataSource.getAccessToken() }
        coVerify { localDataSource.saveToken("sample_access_token") }
    }

    @Test
    fun `isUserAuthenticated returns false when no token is available`() = runBlockingTest {
        // Arrange
        coEvery { localDataSource.loadToken() } just Runs
        coEvery { localDataSource.getToken() } returns null
        coEvery { remoteDataSource.getAccessToken() } returns null

        // Act
        val result = repository.isUserAuthenticated()

        // Assert
        assertFalse(result)
        coVerify { localDataSource.loadToken() }
        coVerify { remoteDataSource.getAccessToken() }
    }
}
