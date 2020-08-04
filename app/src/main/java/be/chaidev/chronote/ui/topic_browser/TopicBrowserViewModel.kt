package be.chaidev.chronote.ui.topic_browser

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.data.repository.TopicsRepository
import be.chaidev.chronote.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class TopicBrowserViewModel @ViewModelInject internal constructor(
    private val topicsRepository: TopicsRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Topic>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<Topic>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: TopicStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is TopicStateEvent.GetTopicEvents -> {
                    topicsRepository.getTopics()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class TopicStateEvent {
    object GetTopicEvents : TopicStateEvent()

    object None : TopicStateEvent()
}