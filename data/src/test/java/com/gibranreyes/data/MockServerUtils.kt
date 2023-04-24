package com.gibranreyes.data

import com.gibranreyes.data.remote.service.ServiceConfiguration.TIMEOUT
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.nio.charset.StandardCharsets
import java.util.Date
import java.util.concurrent.TimeUnit

object MockServerUtils {

    private fun moshiFactory(): MoshiConverterFactory {
        val moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .add(KotlinJsonAdapterFactory())
            .build()
        return MoshiConverterFactory.create(moshi)
    }

    fun <T> restDataSource(
        mockWebServer: MockWebServer,
        clazz: Class<T>,
        coroutineScope: CoroutineScope,
    ): T = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(
            OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY },
                )
                .build(),
        )
        .addConverterFactory(moshiFactory())
        .addCallAdapterFactory(ApiResponseCallAdapterFactory.create(coroutineScope))
        .build()
        .create(clazz)

    fun MockWebServer.enqueueResponse(fileName: String, code: Int = 200) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            val response = MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
            enqueue(
                response,
            )
        }
    }
}
