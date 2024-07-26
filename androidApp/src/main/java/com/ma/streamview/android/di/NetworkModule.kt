package com.ma.streamview.android.di

import android.content.Context
import com.ma.streamview.KtorClientFactory
import com.ma.streamview.TWITCH_HELIX_BASE_URL
import com.ma.streamview.data.preferences.PlatformPreferences
import com.ma.streamview.data.repo.MediaRepository
import com.ma.streamview.data.repo.MediaRepositoryImpl
import com.ma.streamview.data.repo.source.MediaLocalDataSource
import com.ma.streamview.data.repo.source.MediaRemoteDataSource
import com.ma.streamview.data.preferences.PlatformContext
import com.ma.streamview.data.repo.source.DatabaseDriverFactory
import com.ma.streamview.data.repo.source.StreamDatabaseFactory
import com.ma.streamview.database.StreamDatabase
import com.ma.streamview.services.GQLService
import com.ma.streamview.services.GQLServiceImpl
import com.ma.streamview.services.HelixService
import com.ma.streamview.services.HelixServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return KtorClientFactory().build()
    }

    @Singleton
    @Provides
    fun provideHelixService(httpClient: HttpClient): HelixService {
        return HelixServiceImpl(httpClient.config {
            defaultRequest {
//                    url(TWITCH_HELIX_BASE_URL)
//                    headers {
//                        header(HttpHeaders.ContentType, ContentType.Application.Json)
//                    }
            }
        })
    }
    @Singleton
    @Provides
    fun provideGQLService(httpClient: HttpClient): GQLService {
        return GQLServiceImpl(httpClient)
    }


    @Singleton
    @Provides
    fun provideMediaRemoteDataSource(helixService: HelixService,gqlService: GQLService): MediaRemoteDataSource {
        return MediaRemoteDataSource(helixService = helixService, gqlService = gqlService)
    }

//    fun providePlatformContext(
//        @ApplicationContext context: Application,
//    ): PlatformPreferences {
//        return PlatformPreferences(context)
//    }

    @Singleton
    @Provides
    fun provideMediaLocalDataSource(
        platformContext: PlatformContext,
        streamDatabase: StreamDatabase
    ): MediaLocalDataSource {
        return MediaLocalDataSource(
            preferences = PlatformPreferences(platformContext),
            db = streamDatabase
        )
    }

    @Singleton
    @Provides
    fun provideAppRepository(
        mediaRemoteDataSource: MediaRemoteDataSource,
        mediaLocalDataSource: MediaLocalDataSource
    ): MediaRepository {
        return MediaRepositoryImpl(
            remoteDataSource = mediaRemoteDataSource,
            localDataSource = mediaLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun provideBasloqDatabase(
       @ApplicationContext context: Context
    ): StreamDatabase {
        return StreamDatabaseFactory(
            driver = DatabaseDriverFactory(context)
        ).createDatabase()
    }


}