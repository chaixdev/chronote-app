package be.chaidev.chronote.repository

import android.util.Log
import be.chaidev.chronote.datasources.api.ApiResponseHandler
import be.chaidev.chronote.datasources.api.dto.GenericResponse
import be.chaidev.chronote.datasources.api.dto.TopicDto
import be.chaidev.chronote.datasources.api.retrofit.StreamarksApi
import be.chaidev.chronote.datasources.cache.dao.TopicDao
import be.chaidev.chronote.datasources.cache.entity.NoteEntity
import be.chaidev.chronote.datasources.cache.entity.TopicEntity
import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.system.Device
import be.chaidev.chronote.ui.mvi.*
import be.chaidev.chronote.ui.topic.state.TopicViewState
import be.chaidev.chronote.util.Constants
import be.chaidev.chronote.util.ErrorHandling.Companion.ERROR_UNKNOWN
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@FlowPreview
class TopicsRepositoryImpl
@Inject
constructor(
    val topicsDao: TopicDao,
    val api: StreamarksApi,
    val system: Device
) : TopicsRepository {
    override fun getTopics(
        stateEvent: StateEvent
    ): Flow<DataState<TopicViewState>> {

        return object : NetworkBoundResource<List<TopicDto>, List<Topic>, TopicViewState>(
            dispatcher = IO,
            stateEvent = stateEvent,
            apiCall = { api.getTopics() },
            cacheCall = { topicsDao.getTopicsWithNotes().map { it.toTopic() } }
        ) {
            override suspend fun updateCache(topicDtoList: List<TopicDto>) {

                withContext(IO) {
                    for (topicDto in topicDtoList) {
                        try {
                            // Launch each insert as a separate job to be executed in parallel
                            launch {
                                val topic = topicDto.toTopic()
                                val topicEntity = TopicEntity.fromTopic(topic)
                                val notes = NoteEntity.fromNotes(topic.notes, topicDto.id)

                                topicsDao.insertTopic(topicEntity)
                                topicsDao.insertNotes(notes)

                            }
                        } catch (e: Exception) {
                            Log.e(
                                Constants.TAG,
                                "updateLocalDb: error updating cache data on topic ${topicDto.id}:${topicDto.subject.title} " +
                                        "${e.message}"
                            )
                        }
                    }
                }
            }

            override fun handleCacheSuccess(resultObj: List<Topic>): DataState<TopicViewState> {
                val viewState = TopicViewState(
                    topicBrowser = TopicViewState.TopicBrowser(
                        topicListData = resultObj
                    )
                )
                return DataState.data(
                    response = null,
                    data = viewState,
                    stateEvent = stateEvent
                )
            }

        }.result

    }

    override fun updateTopic(
        topic: Topic,
        stateEvent: StateEvent
    ): Flow<DataState<TopicViewState>> = flow {
        val apiResult = safeApiCall(IO) {
            api.updateTopic(
                TopicDto.fromTopic(topic)
            )
        }
        emit(
            object : ApiResponseHandler<TopicViewState, TopicDto>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: TopicDto): DataState<TopicViewState> {
                    val updatedTopic = resultObj.toTopic()
                    val topicEntity = TopicEntity.fromTopic(updatedTopic)
                    val notes = NoteEntity.fromNotes(updatedTopic.notes, updatedTopic.id)
                    topicsDao.updateTopic(topicEntity)
                    topicsDao.updateNotes(notes)

                    return DataState.data(
                        response = Response(
                            message = "Update successful",
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Success()
                        ),
                        data = TopicViewState(
                            viewTopic = TopicViewState.ViewTopic(
                                topic = updatedTopic
                            )
                        ),
                        stateEvent = stateEvent
                    )

                }

            }.getResult()
        )
    }


    override fun deleteTopic(topic: Topic, stateEvent: StateEvent): Flow<DataState<TopicViewState>> = flow {

        val apiResult = safeApiCall(IO) {
            api.deleteTopic(topic.id)
        }

        emit(
            object : ApiResponseHandler<TopicViewState, GenericResponse>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: GenericResponse): DataState<TopicViewState> {

                    if (resultObj.response == ResponseHandling.SUCCESS_TOPIC_DELETED) {
                        topicsDao.deleteTopic(TopicEntity.fromTopic(topic))
                        topicsDao.deleteNotes(NoteEntity.fromNotes(topic.notes, topic.id))
                        return DataState.data(
                            response = Response(
                                message = ResponseHandling.SUCCESS_TOPIC_DELETED,
                                uiComponentType = UIComponentType.Toast(),
                                messageType = MessageType.Success()
                            ),
                            stateEvent = stateEvent
                        )
                    } else {
                        return buildError(
                            ERROR_UNKNOWN,
                            UIComponentType.Dialog(),
                            stateEvent
                        )
                    }
                }
            }.getResult()
        )
    }
}