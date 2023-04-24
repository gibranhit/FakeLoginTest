package com.gibranreyes.data.remote.service

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.gibranreyes.data.BuildConfig
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit

object ServiceConfiguration {

    const val TIMEOUT: Long = 30

    private val moshiFactory by lazy {
        val moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .add(KotlinJsonAdapterFactory())
            .build()
        MoshiConverterFactory.create(moshi)
    }
    private val okHttpClientFactory by lazy {
        val builder = OkHttpClient.Builder()

        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS)
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            )
            builder.addNetworkInterceptor(StethoInterceptor())
        }
        builder.build()
    }

    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .client(okHttpClientFactory)
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
        .addConverterFactory(moshiFactory)
        .build()
}
