package be.chaidev.chronote.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import be.chaidev.chronote.data.cache.DataCache
import be.chaidev.chronote.data.cache.dao.TopicDao
import be.chaidev.chronote.data.cache.entity.TopicEntity
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.data.network.ApiSuccessResponse
import be.chaidev.chronote.data.network.GenericApiResponse
import be.chaidev.chronote.data.network.dto.TopicDto
import be.chaidev.chronote.data.network.retrofit.StreamarksApi
import be.chaidev.chronote.system.Device
import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.topic.state.TopicViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopicsRepository
@Inject
constructor(
    val cache: DataCache,
    val streamarksApi: StreamarksApi,
    val system: Device
) : JobManager("TopicsRepository") {
    private val topics: List<Topic> = emptyList()
    fun getTopics(filterAndOrder: String): LiveData<DataState<TopicViewState>> {

        return object : NetworkBoundResource<TopicDto, List<TopicEntity>, TopicViewState>(
            system.isInternetAvailable(),
            true,
            false,
            true
        ) {
            override suspend fun createCacheRequestAndReturn() {
                withContext(Dispatchers.Main) {
                    // finishing by viewing db cache
                    // addSource: LiveData method to have anohter trigger source
                    result.addSource(loadFromCache()) { onCompleteJob(DataState.data(it, null)) }
                }
            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<TopicDto>) {
                TODO("Not yet implemented")
            }

            override fun createCall(): LiveData<GenericApiResponse<TopicDto>> {
                TODO("Not yet implemented")
            }

            override fun loadFromCache(): LiveData<TopicViewState> {
                return cache.returnOrderedTopicQuery(filterAndOrder = filterAndOrder)
                    .switchMap {
                        object : LiveData<TopicViewState>() {
                            override fun onActive() {
                                super.onActive()
                                value = TopicViewState(
                                    TopicViewState.TopicBrowser(
                                        topicsList = it,
                                        isQueryInProgress = true
                                    )
                                )
                            }
                        }
                    }
            }

            override suspend fun updateLocalDb(cacheObject: List<TopicEntity>?) {
                TODO("Not yet implemented")
            }

            override fun setJob(job: Job) {
                TODO("Not yet implemented")
            }

        }.asLiveData()
//        // caching strategy:
//        // 1. 1st emit: 'loading' message
//        emit(DataState.loading(true, ))
//        // 2. 2nd emit: cached values if present.
//        emit(DataState.data(cache.getAllTopics()))
//        try {
//            // 3. launch network query
//            Log.d("network", "calling streamarksApi.getTopics()")
//            val networkEntities = streamarksApi.getTopics()
//            // 4. let cache handle caching strategy
//            val topics = networkEntities.map(TopicDto::toTopic)
//            for (topic in topics) {
//                cache.saveTopic(topic)
//            }
//
//            // 5. 3d emit: whatever is in cache
//
//            emit(DataState.Success(cache.getAllTopics()))
//        } catch (e: Exception) {
//            Log.e(this.javaClass.simpleName, "getTopics:  ",e)
//            emit(DataState.Error(e))
    }
}
