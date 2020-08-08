package be.chaidev.chronote.ui.topic.viewmodel

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import be.chaidev.chronote.repository.TopicsRepository
import be.chaidev.chronote.ui.AbstractViewModel
import be.chaidev.chronote.ui.mvi.AbsentLiveData
import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.topic.state.TopicStateEvent
import be.chaidev.chronote.ui.topic.state.TopicStateEvent.*
import be.chaidev.chronote.ui.topic.state.TopicViewState
import be.chaidev.chronote.util.SharedPreferenceKeys
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TopicBrowserViewModel
@ViewModelInject
internal constructor(
    private val topicsRepository: TopicsRepository,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : AbstractViewModel<TopicStateEvent, TopicViewState>() {

    override fun handleStateEvent(stateEvent: TopicStateEvent): LiveData<DataState<TopicViewState>> {
        when (stateEvent) {
            is LoadTopicsEvent -> {
                //todo: implement
                return AbsentLiveData.create()

            }

            is DeleteTopicEvent -> {

                //todo: implement
                return AbsentLiveData.create()
            }

            is UpdateTopicEvent -> {
                //todo: implement
                return AbsentLiveData.create()
            }

            is None -> {
                return AbsentLiveData.create()
            }
        }
    }

    override fun initNewViewState(): TopicViewState {
        return TopicViewState()
    }

    fun saveFilterOptions(filter: String, order: String) {
        editor.putString(SharedPreferenceKeys.TOPIC_BROWSER_FILTER, filter)
        editor.apply()

        editor.putString(SharedPreferenceKeys.TOPIC_BROWSER_ORDER, order)
        editor.apply()
    }

    fun cancelActiveJobs() {
//        topicsRepository.cancelActiveJobs() // cancel active jobs
        handlePendingData() // hide progress bar
    }

    fun handlePendingData() {
        setStateEvent(None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

}
