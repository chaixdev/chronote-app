package be.chaidev.chronote.ui.topic.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import be.chaidev.chronote.repository.TopicsRepository
import be.chaidev.chronote.ui.AbstractViewModel
import be.chaidev.chronote.ui.mvi.AbsentLiveData
import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.topic.state.TopicStateEvent
import be.chaidev.chronote.ui.topic.state.TopicStateEvent.*
import be.chaidev.chronote.ui.topic.state.TopicViewState
import be.chaidev.chronote.util.Constants.TAG
import be.chaidev.chronote.util.SharedPreferenceKeys
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TopicBrowserViewModel
@ViewModelInject
internal constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val topicsRepository: TopicsRepository,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : AbstractViewModel<TopicStateEvent, TopicViewState>() {

    override fun handleStateEvent(stateEvent: TopicStateEvent): LiveData<DataState<TopicViewState>> {

        when (stateEvent) {
            is LoadTopicsEvent -> {
                Log.d(TAG, "TopicBrowserViewModel handleStateEvent: LoadTopicsEvent}")
                return topicsRepository.getTopics()
            }

            is DeleteTopicEvent -> {
                Log.d(TAG, "TopicBrowserViewModel handleStateEvent: DeleteTopicEvent}")
                return topicsRepository.deleteTopic(topic = getTopic())
            }

            is UpdateTopicEvent -> {
                Log.d(TAG, "TopicBrowserViewModel handleStateEvent: UpdateTopicEvent}")
                return AbsentLiveData.create()
            }

            is None -> {
                Log.d(TAG, "TopicBrowserViewModel handleStateEvent: None}")
                return AbsentLiveData.create()
            }
        }
    }

    override fun initNewViewState(): TopicViewState {
        Log.d(TAG, "TopicBrowserViewModel init new view state")
        return TopicViewState()
    }

    fun saveFilterOptions(filter: String, order: String) {
        editor.putString(SharedPreferenceKeys.TOPIC_BROWSER_FILTER, filter)
        editor.apply()

        editor.putString(SharedPreferenceKeys.TOPIC_BROWSER_ORDER, order)
        editor.apply()
    }

    fun cancelActiveJobs() {
        topicsRepository.cancelActiveJobs() // cancel active jobs
        handlePendingData() // hide progress bar
    }

    fun handlePendingData() {
        setStateEvent(None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

    fun resetPage() {
        val update = getCurrentViewStateOrNew()
        setViewState(update)
    }

    fun loadTopics() {
        Log.d(TAG, "TopicViewModel: Attempting to load topics")
        setQueryInProgress(true)
        resetPage()
        setStateEvent(LoadTopicsEvent())

    }
}
