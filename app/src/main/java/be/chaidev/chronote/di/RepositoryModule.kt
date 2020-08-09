package be.chaidev.chronote.di

import android.app.Application
import be.chaidev.chronote.datasources.cache.dao.TopicDao
import be.chaidev.chronote.datasources.network.retrofit.StreamarksApi
import be.chaidev.chronote.repository.TopicsRepository
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
        topicDao:TopicDao,
        streamarksApi: StreamarksApi,
        device: Device
    ): TopicsRepository {
        return TopicsRepository(topicDao,streamarksApi, device)
    }

    @Singleton
    @Provides
    fun provideDevice(application:Application): Device {
        return Device(application)
    }
}