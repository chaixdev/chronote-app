package be.chaidev.chronote.ui.topic.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import be.chaidev.chronote.repository.TopicsRepository
import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.mvi.StateEvent
import be.chaidev.chronote.ui.topic.state.TopicStateEvent
import be.chaidev.chronote.ui.topic.state.TopicStateEvent.LoadTopicsEvent
import be.chaidev.chronote.ui.topic.state.TopicViewState
import be.chaidev.chronote.util.ErrorStack
import be.chaidev.chronote.util.ErrorState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class TopicBrowserViewModel
@ViewModelInject
constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val topicRepository: TopicsRepository
) : ViewModel() {

    val CLASS_NAME = "MainViewModel"

    private val dataChannel = BroadcastChannel<DataState<TopicViewState>>(50)

    private val _viewState: MutableLiveData<TopicViewState> = MutableLiveData()

    val errorStack = ErrorStack()

    val errorState: LiveData<ErrorState> = errorStack.errorState

    val viewState: LiveData<TopicViewState>
        get() = _viewState

    init {
        setupChannel()
    }

    private fun setupChannel() {
        dataChannel
            .asFlow()
            .onEach { dataState ->
                dataState.data?.let { data ->
                    handleNewData(dataState.stateEvent, data)
                }
                dataState.error?.let { error ->
                    handleNewError(dataState.stateEvent, error)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun offerToDataChannel(dataState: DataState<TopicViewState>) {
        if (!dataChannel.isClosedForSend) {
            dataChannel.offer(dataState)
        }
    }


    /**
     * this is the operation that is called from UI of elsewhere, that raises the event for the other layers to react to.
     *
     * **/
    fun setStateEvent(stateEvent: TopicStateEvent) {
        when (stateEvent) {
            is LoadTopicsEvent -> {
                launchJob(
                    stateEvent,
                    topicRepository.getTopics(stateEvent)
                )
            }
        }
    }

    private fun handleNewError(stateEvent: StateEvent, error: ErrorState) {
        appendErrorState(error)
        removeJobFromCounter(stateEvent.toString())
    }


    fun handleNewData(stateEvent: StateEvent?, data: TopicViewState) {

        data.topicBrowser.topicListData?.let { topics ->
            setTopicListData(topics)
        }


        data.viewTopic.topic?.let { topic ->
            setTopic(topic)
        }

        removeJobFromCounter(stateEvent.toString())
    }

    /**
     *
     * After noticing a StateEvent, launch jobs as coroutine flow. Datachannel keeps track of all the different jobs and updates viewmodel Livedata as they get completed
     *
     * **/
    private fun launchJob(stateEvent: StateEvent, jobFunction: Flow<DataState<TopicViewState>>) {
        if (!isJobAlreadyActive(stateEvent.toString())) {
            addJobToCounter(stateEvent.toString())
            jobFunction
                .onEach { dataState ->
                    offerToDataChannel(dataState)
                }
                .launchIn(viewModelScope)
        }
    }


    /**
     * When a Job completes and setViewState is caledm all subscribers to the viewstate LiveData get notified and can perform the UI update
     * **/
    fun setViewState(viewState: TopicViewState) {
        _viewState.value = viewState
    }


}
