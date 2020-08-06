package be.chaidev.chronote.data.repository

import android.util.Log
import be.chaidev.chronote.data.cache.DataCache
import be.chaidev.chronote.data.cache.entity.NoteEntity
import be.chaidev.chronote.data.cache.entity.TopicEntity
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.data.network.dto.TopicDto
import be.chaidev.chronote.data.network.retrofit.StreamarksApi
import be.chaidev.chronote.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TopicsRepository
constructor(
    private val cache:DataCache,
    private val streamarksApi: StreamarksApi
) {
    private val topics: List<Topic> = emptyList()
    suspend fun getTopics(): Flow<DataState<List<Topic>>> = flow {

        // caching strategy:
        // 1. 1st emit: 'loading' message
        emit(DataState.Loading)
        // 2. 2nd emit: cached values if present.
        emit(DataState.Success(cache.getAllTopics()))
        try {
            // 3. launch network query
            Log.d("network", "calling streamarksApi.getTopics()")
            val networkEntities = streamarksApi.getTopics()
            // 4. let cache handle caching strategy
            val topics = networkEntities.map(TopicDto::toTopic)
            for (topic in topics) {
                cache.saveTopic(topic)
            }

            // 5. 3d emit: whatever is in cache

            emit(DataState.Success(cache.getAllTopics()))
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, "getTopics:  ",e)
            emit(DataState.Error(e))
        }
    }
}