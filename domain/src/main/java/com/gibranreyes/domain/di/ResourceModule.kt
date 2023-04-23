package com.gibranreyes.domain.di

import com.gibranreyes.domain.converter.ResourceConverter
import com.gibranreyes.domain.converter.ResourceConverterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ResourceModule {

    @Binds
    abstract fun bindResources(
        resourceConverter: ResourceConverterImpl,
    ): ResourceConverter

}
