package be.chaidev.chronote.repository

import be.chaidev.chronote.di.MainScope
import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.mvi.StateEvent
import be.chaidev.chronote.ui.topic.state.TopicViewState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@FlowPreview
@MainScope
interface TopicsRepository {

    fun getTopics(
        stateEvent: StateEvent
    ): Flow<DataState<TopicViewState>>

}

