package com.hnc.company.lototools.di

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.TlsVersion
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
object OkHttpModule {

    private const val TIMEOUT = 60L

    @Provides
    @Singleton
    fun provideConnectionSpecs(): ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
        .tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
        .allEnabledCipherSuites()
        .build()

    @Provides
    @Singleton
    @SuppressLint("CustomX509TrustManager")
    fun provideTrustManager(): X509TrustManager = object : X509TrustManager {
        override fun checkClientTrusted(
            chain: Array<out X509Certificate>?,
            authType: String?
        ) = Unit

        override fun checkServerTrusted(
            chain: Array<out X509Certificate>?,
            authType: String?
        ) = Unit

        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        connectionSpec: ConnectionSpec,
        trustManager: X509TrustManager,
        cacheInterceptor: CacheInterceptor,
        responseInterceptor: ResponseInterceptor,
        convert400Interceptor: Convert400Interceptor,
    ): OkHttpClient {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, null, SecureRandom())
        return OkHttpClient().newBuilder()
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectionSpecs(listOf(connectionSpec, ConnectionSpec.CLEARTEXT))
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .addInterceptor(cacheInterceptor)
            .addInterceptor(responseInterceptor)
            .addInterceptor(convert400Interceptor)
            .build()
    }
}

@Singleton
class ResponseInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val isRefreshDomain = originalResponse.request.url.toString().contains("refresh")
        if (originalResponse.code == 200 && !isRefreshDomain) {
            val body = originalResponse.peekBody(Long.MAX_VALUE).string()
            if (body.contains("\"httpStatus\":401")) {
                return originalResponse.newBuilder()
                    .code(401) // Set HTTP status to 401
                    .message("Unauthorized") // Optional: Customize the response message
                    .body(originalResponse.body)
                    .build()
            }
        }
        return originalResponse
    }
}

@Singleton
class Convert400Interceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.code == 404 || originalResponse.code == 500) {
            val body = originalResponse.peekBody(Long.MAX_VALUE).string()
            if (body.contains("\"success\":false")) {
                return originalResponse.newBuilder()
                    .code(200) // Set HTTP status to 401
                    .message(originalResponse.message) // Optional: Customize the response message
                    .body(originalResponse.body)
                    .build()
            }
        }
        return originalResponse
    }
}


class CacheInterceptor @Inject constructor() : Interceptor {

    companion object {
        private const val TAG = "CacheInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        try {
            val response = chain.proceed(request)
            if (request.method == "GET") {
                val requestCacheControl = request.header("Cache-Control")
                if (requestCacheControl != null) {
                    Log.d(TAG, "Modifying response Cache-Control header for GET request.")
                    return response.newBuilder()
                        .header("Cache-Control", requestCacheControl)
                        .build()
                }
            }
            return response
        } catch (e: Exception) {
            Log.e(TAG, "Error intercepting request/response", e)
            throw e
        }
    }
}
