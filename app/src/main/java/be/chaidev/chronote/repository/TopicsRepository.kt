package be.chaidev.chronote.repository

import androidx.lifecycle.LiveData
import be.chaidev.chronote.data.cache.dao.TopicDao
import be.chaidev.chronote.data.network.ApiSuccessResponse
import be.chaidev.chronote.data.network.GenericApiResponse
import be.chaidev.chronote.data.network.dto.TopicDto
import be.chaidev.chronote.data.network.retrofit.StreamarksApi
import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.system.Device
import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.topic.state.TopicViewState
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopicsRepository
@Inject
constructor(
    val topicsDao: TopicDao,
    val api: StreamarksApi,
    val system: Device
) : JobManager("TopicsRepository") {
    private val topics: List<Topic> = emptyList()
    fun getTopics(filterAndOrder: String): LiveData<DataState<TopicViewState>> {

        return object : NetworkBoundResource<List<TopicDto>, List<Topic>, TopicViewState>(
            system.isInternetAvailable(),
            true,
            false,
            true
        ) {
            override suspend fun createCacheRequestAndReturn() {
                withContext(Main) {
                    // finishing by viewing db cache
                    // addSource: LiveData method to have anohter trigger source
                    result.addSource(loadFromCache()) { onCompleteJob(DataState.data(it, null)) }
                }
            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<List<TopicDto>>) {
                val topicList = response.body.map(TopicDto::toTopic)
                updateLocalDb(topicList)
                createCacheRequestAndReturn()
            }

            override fun createCall(): LiveData<GenericApiResponse<List<TopicDto>>> {
                return api.getTopics()
            }

            override fun loadFromCache(): LiveData<TopicViewState> {
                TODO()
//                return topicsDao.returnOrderedQuery(filterAndOrder = filterAndOrder)
//                    .switchMap {
//                        object : LiveData<TopicViewState>() {
//                            override fun onActive() {
//                                super.onActive()
//                                value = TopicViewState(
//                                    TopicViewState.TopicBrowser(
//                                        topicsList = it,
//                                        isQueryInProgress = true
//                                    )
//                                )
//                            }
//                        }
//                    }
            }

            override suspend fun updateLocalDb(cacheObject: List<Topic>?) {
                TODO()
//                if(cacheObject != null){
//                    withContext(IO){
//                        for(topic in cacheObject){
//                            try{
//                                // new job for each element to insert (parallel)
//                                launch{
//                                    Log.d(Constants.TAG,"updating cache for topic ${topic.id}:${topic.subject.title}" )
//                                    topicsDao.save(topic)
//                                }
//                            }catch(e: Exception){
//                                Log.e(Constants.TAG,"Error while updating cache for topic id ${topic.id}" )
//                            }
//                        }
//                    }
//                }

            }

            override fun setJob(job: Job) {
                addJob("getTopics",job)
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
