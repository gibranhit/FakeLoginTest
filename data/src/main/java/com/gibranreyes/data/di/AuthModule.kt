package com.gibranreyes.data.di

import com.gibranreyes.data.repository.AuthRepositoryImpl
import com.gibranreyes.data.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AuthModule {

    @Binds
    abstract fun provideAuthRepository(
        authRepository: AuthRepositoryImpl,
    ): AuthRepository
}
