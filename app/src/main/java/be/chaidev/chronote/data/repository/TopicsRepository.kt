package be.chaidev.chronote.data.repository

import android.util.Log
import be.chaidev.chronote.data.cache.CacheMapper
import be.chaidev.chronote.data.cache.dao.TopicDao
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.data.network.retrofit.NetworkMapper
import be.chaidev.chronote.data.network.retrofit.StreamarksApi
import be.chaidev.chronote.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TopicsRepository constructor(
    private val topicDao: TopicDao,
    private val streamarksApi: StreamarksApi,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {

    suspend fun getTopics(): Flow<DataState<List<Topic>>> = flow{
        emit(DataState.Loading)
        try{
            Log.d("network", "calling streamarksApi.getTopics()")
            val networkEntities = streamarksApi.getTopics()
            val topics = networkMapper.mapFromEntitiesList(networkEntities)
            for(topic in topics){
                topicDao.insert(cacheMapper.mapToEntity(topic))
            }

            val cachedTopics = topicDao.getAll()
            emit(DataState.Success(cacheMapper.mapFromEntitiesList(cachedTopics)))
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }
}