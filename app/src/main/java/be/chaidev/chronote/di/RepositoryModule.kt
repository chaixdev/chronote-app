package be.chaidev.chronote.di

import android.app.Application
import be.chaidev.chronote.datasources.api.retrofit.StreamarksApi
import be.chaidev.chronote.datasources.cache.dao.TopicDao
import be.chaidev.chronote.repository.TopicsRepository
import be.chaidev.chronote.repository.TopicsRepositoryImpl
import be.chaidev.chronote.system.Device
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@FlowPreview
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
        return TopicsRepositoryImpl(topicDao, streamarksApi, device)
    }

    @Singleton
    @Provides
    fun provideDevice(application:Application): Device {
        return Device(application)
    }
}