package be.chaidev.chronote.di

import be.chaidev.chronote.data.cache.DataCache
import be.chaidev.chronote.data.cache.dao.TopicDao
import be.chaidev.chronote.data.network.retrofit.StreamarksApi
import be.chaidev.chronote.data.repository.TopicsRepository
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
        streamarksApi: StreamarksApi
    ): TopicsRepository {
        return TopicsRepository(dataCache, streamarksApi)
    }
}