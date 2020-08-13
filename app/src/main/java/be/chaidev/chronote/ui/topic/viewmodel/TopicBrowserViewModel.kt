package be.chaidev.chronote.ui.topic.viewmodel

import android.content.SharedPreferences
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import be.chaidev.chronote.repository.TopicsRepository
import be.chaidev.chronote.ui.AbstractViewModel
import be.chaidev.chronote.ui.mvi.*
import be.chaidev.chronote.ui.topic.state.TopicStateEvent.*
import be.chaidev.chronote.ui.topic.state.TopicViewState
import be.chaidev.chronote.util.ErrorHandling.Companion.INVALID_STATE_EVENT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@FlowPreview
@ExperimentalCoroutinesApi
class TopicBrowserViewModel
@ViewModelInject
internal constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val topicsRepository: TopicsRepository,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : AbstractViewModel<TopicViewState>() {

    override fun handleNewData(data: TopicViewState) {

        data.topicBrowser.let { topicBrowser ->

            topicBrowser.topicListData?.let { listData ->
                setTopicListData(listData)
            }
        }

        data.viewTopic.let { viewTopic ->

            viewTopic.topic?.let { topic ->
                setTopic(topic)
            }
        }

        data.updatedTopic.let { updatedBlogFields ->
            updatedBlogFields.topic?.let { topic ->
                setTopic(topic)
            }
        }
    }


    override fun setStateEvent(stateEvent: StateEvent) {
        if (!isJobAlreadyActive(stateEvent)) {

            val job: Flow<DataState<TopicViewState>> = when (stateEvent) {

                is LoadTopicsEvent -> {
                    topicsRepository.getTopics(
                        stateEvent = stateEvent
                    )
                }

                is DeleteTopicEvent -> {
                    topicsRepository.deleteTopic(stateEvent.topic, stateEvent)
                }

                is UpdateTopicEvent -> {
                    topicsRepository.updateTopic(
                        stateEvent.topic,
                        stateEvent
                    )
                }

                else -> {
                    flow {
                        emit(
                            DataState.error(
                                response = Response(
                                    message = INVALID_STATE_EVENT,
                                    uiComponentType = UIComponentType.None(),
                                    messageType = MessageType.Error()
                                ),
                                stateEvent = stateEvent
                            )
                        )
                    }
                }
            }
            launchJob(stateEvent, job)
        }
    }

    override fun initNewViewState(): TopicViewState {
        return TopicViewState()
    }
}
