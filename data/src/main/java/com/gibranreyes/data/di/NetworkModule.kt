package com.gibranreyes.data.di

import com.gibranreyes.data.remote.api.AuthServices
import com.gibranreyes.data.remote.service.ServiceConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = ServiceConfiguration.provideRetrofitInstance()

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthServices =
        retrofit.create(AuthServices::class.java)
}
