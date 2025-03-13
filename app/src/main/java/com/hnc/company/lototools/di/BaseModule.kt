package com.hnc.company.lototools.di

import com.hnc.company.lototools.domain.repository.BaseRepository
import com.hnc.company.lototools.data.repository.BaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface PackageRepositoryModule {
    @Singleton
    @Binds
    fun bindGPackageRepository(baseRepositoryImpl: BaseRepositoryImpl): BaseRepository
}
