package be.chaidev.chronote.di

import be.chaidev.chronote.data.cache.CacheMapper
import be.chaidev.chronote.data.cache.dao.TopicDao
import be.chaidev.chronote.data.network.retrofit.NetworkMapper
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
        topicDao: TopicDao,
        streamarksApi: StreamarksApi,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): TopicsRepository {
        return TopicsRepository(topicDao, streamarksApi, cacheMapper, networkMapper)
    }
}