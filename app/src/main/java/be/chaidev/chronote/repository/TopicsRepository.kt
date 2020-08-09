package be.chaidev.chronote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import be.chaidev.chronote.datasources.cache.dao.TopicDao
import be.chaidev.chronote.datasources.cache.entity.NoteEntity
import be.chaidev.chronote.datasources.cache.entity.TopicEntity
import be.chaidev.chronote.datasources.cache.entity.TopicWithNotes
import be.chaidev.chronote.datasources.network.ApiSuccessResponse
import be.chaidev.chronote.datasources.network.GenericApiResponse
import be.chaidev.chronote.datasources.network.dto.GenericResponse
import be.chaidev.chronote.datasources.network.dto.TopicDto
import be.chaidev.chronote.datasources.network.retrofit.StreamarksApi
import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.repository.ResponseHandling.SUCCESS_TOPIC_DELETED
import be.chaidev.chronote.system.Device
import be.chaidev.chronote.ui.mvi.AbsentLiveData
import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.mvi.Response
import be.chaidev.chronote.ui.mvi.ResponseType
import be.chaidev.chronote.ui.topic.state.TopicViewState
import be.chaidev.chronote.util.Constants
import be.chaidev.chronote.util.Constants.TAG
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopicsRepository
@Inject
constructor(
    val topicsDao: TopicDao,
    val api: StreamarksApi,
    val system: Device
) : JobManager("TopicsRepository") {

    fun getTopics(): LiveData<DataState<TopicViewState>> {
        Log.d(TAG, "TopicRepo getTopics")
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

                return topicsDao.getTopicsWithNotes()
                    .map {
                        TopicViewState(
                            TopicViewState.TopicBrowser(
                                topicsList = it.map(TopicWithNotes::toTopic),
                                isQueryInProgress = true
                            )
                        )
                    }.asLiveData()
            }

            override suspend fun updateLocalDb(cacheObject: List<Topic>?) {
                if (cacheObject != null) {
                    withContext(IO) {
                        for (topic in cacheObject) {
                            try {
                                // new job for each element to insert (parallel)
                                launch {
                                    Log.d(Constants.TAG, "updating cache for topic ${topic.id}:${topic.subject.title}")
                                    topicsDao.updateTopic(TopicEntity.fromTopic(topic))
                                    topicsDao.updateNotes(NoteEntity.fromNotes(topic.notes, topic.id))
                                }
                            } catch (e: Exception) {
                                Log.e(Constants.TAG, "Error while updating cache for topic id ${topic.id}")
                            }
                        }
                    }
                }
            }

            override fun setJob(job: Job) {
                addJob("getTopics", job)
            }

        }.asLiveData()
    }

    fun deleteTopic(topic: Topic): LiveData<DataState<TopicViewState>> {
        return object : NetworkBoundResource<GenericResponse, Topic, TopicViewState>(
            system.isInternetAvailable(),
            true,
            true,
            false
        ) {

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<GenericResponse>) {
                if (response.body.response == SUCCESS_TOPIC_DELETED) {
                    updateLocalDb(topic)
                }


            }

            override fun createCall(): LiveData<GenericApiResponse<GenericResponse>> {
                return api.deleteTopic(topic.id)
            }

            override fun loadFromCache(): LiveData<TopicViewState> {
                return AbsentLiveData.create()
            }

            override suspend fun updateLocalDb(cacheObject: Topic?) {
                cacheObject?.let { topic ->
                    topicsDao.deleteNotes(NoteEntity.fromNotes(topic.notes, topic.id))
                    topicsDao.deleteTopic(TopicEntity.fromTopic(topic))
                    onCompleteJob(
                        DataState.data(
                            null,
                            Response(SUCCESS_TOPIC_DELETED, ResponseType.Toast())
                        )
                    )
                }
            }

            override fun setJob(job: Job) {
                addJob("deleteTopic", job)
            }

        }.asLiveData()
    }
}
