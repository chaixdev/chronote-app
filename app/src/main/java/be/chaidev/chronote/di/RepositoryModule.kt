package be.chaidev.chronote.di

import android.app.Application
import be.chaidev.chronote.data.cache.DataCache
import be.chaidev.chronote.data.cache.dao.TopicDao
import be.chaidev.chronote.data.network.retrofit.StreamarksApi
import be.chaidev.chronote.data.repository.TopicsRepository
import be.chaidev.chronote.system.Device
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTopicsRepository(
        dataCache:DataCache,
        streamarksApi: StreamarksApi,
        device: Device
    ): TopicsRepository {
        return TopicsRepository(dataCache, streamarksApi, device)
    }

    @Singleton
    @Provides
    fun provideDevice(application:Application): Device {
        return Device(application)
    }
}