
package com.base.di

import com.base.data.remote.BreedRepositoryImpl
import com.base.data.local.LocalDataSource
import com.base.data.local.LocalDataSourceImpl
import com.base.data.remote.RemoteDataSourceImpl
import com.base.data.remote.BreedRepository
import com.base.data.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Tells Dagger this is a Dagger module
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: BreedRepositoryImpl): BreedRepository

    @Binds
    @Singleton
    abstract fun provideLocalDataSource(dataRepository: LocalDataSourceImpl): LocalDataSource

    @Binds
    @Singleton
    abstract fun provideRemoteDataSource(dataRepository: RemoteDataSourceImpl): RemoteDataSource

}
