package com.hnc.company.lototools.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object KtorModule {
    private const val REQUEST_TIMEOUT = 10000L

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideHttpClient(okHttpClient: OkHttpClient): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    }
                )
            }

            engine {
                config {
                    // this: OkHttpClient.Builder
                    threadsCount = 8
                    pipelining = false
                }

                preconfigured = okHttpClient
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("@@@", "Logger Ktor -> $message")
                    }
                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("@@@", "${response.status.value}")
                }
            }

            install(HttpRequestRetry) {
                retryOnExceptionOrServerErrors()
                exponentialDelay()
            }

            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT
            }
        }
    }

    @HttpClientSocket
    @Provides
    fun provideKtorSocketHttpClient(httpClient: HttpClient): HttpClient {
        return httpClient.config {
            install(WebSockets) {
                maxFrameSize = Long.MAX_VALUE
            }

            install(DefaultRequest) {
                runBlocking {
                    withTimeout(2000) {
                        header(HttpHeaders.ContentType, ContentType.Application.Json)
                    }
                }
            }
        }
    }

    @HttpClientNetwork
    @Provides
    fun provideKtorNetworkHttpClient(httpClient: HttpClient): HttpClient {
        httpClient.config {
            install(WebSockets) {
                maxFrameSize = Long.MAX_VALUE
            }

            install(DefaultRequest) {
                runBlocking {
                    withTimeout(2000) {
                        header(HttpHeaders.ContentType, ContentType.Application.Json)
                    }
                }
            }
        }

        return httpClient
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpClientNetwork

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpClientSocket
