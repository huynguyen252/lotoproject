package com.hnc.company.lototools.di

import com.hnc.company.lototools.data.repository.BaseRepositoryImpl
import com.hnc.company.lototools.domain.repository.BaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindRepository(baseRepositoryImpl: BaseRepositoryImpl): BaseRepository
}
