package be.chaidev.chronote.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.chaidev.chronote.ui.mvi.DataChannelManager
import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.mvi.StateEvent
import be.chaidev.chronote.ui.mvi.StateMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

/**this class abstracts core components that every viewmodel should have in our MVI architecture:
 * - viewstate: LiveData containing the data as should be observed and represented by the ui
 * - numActiveJobs: a livedata representing how many active jobs the repository and io scopes are currently handling
 *      (jobs launched by ui that are not terminated or completed)
 * - stateMessage: mesages from a stack that are the result of jobs asynchronously executed
 * **/

@FlowPreview
@ExperimentalCoroutinesApi
abstract class AbstractViewModel<ViewState> : ViewModel() {
    val TAG: String = "AppDebug"

    val dataChannelManager: DataChannelManager<ViewState> = object : DataChannelManager<ViewState>() {

        override fun handleNewData(data: ViewState) {
            this@AbstractViewModel.handleNewData(data)
        }
    }

    // pattern: the MutableLiveData is private, property LiveData is mutable. -> only members of viewmodel can access and emit
    // new values, observers of LiveData can not change it
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()
    val viewState: LiveData<ViewState>
        get() = _viewState

    val numActiveJobs: LiveData<Int> = dataChannelManager.numActiveJobs

    val stateMessage: LiveData<StateMessage?>
        get() = dataChannelManager.messageStack.stateMessage

    // FOR DEBUGGING
    fun getMessageStackSize(): Int {
        return dataChannelManager.messageStack.size
    }

    fun setupChannel() = dataChannelManager.setupChannel()

    abstract fun handleNewData(data: ViewState)

    // this should be implemented in every viewmodel for their own type of stateEvents..
    // in our MVI implementation, thjs is the starting point for all user interaction with the app logic
    abstract fun setStateEvent(stateEvent: StateEvent)

    // once we know what job to execute (from stateevent), throw it in the dataChannelManager and let it handle the job and message stacks
    fun launchJob(
        stateEvent: StateEvent,
        jobFunction: Flow<DataState<ViewState>>
    ) {
        dataChannelManager.launchJob(stateEvent, jobFunction)
    }

    fun areAnyJobsActive(): Boolean {
        return dataChannelManager.numActiveJobs.value?.let {
            it > 0
        } ?: false
    }

    fun isJobAlreadyActive(stateEvent: StateEvent): Boolean {
        Log.d(TAG, "isJobAlreadyActive?: ${dataChannelManager.isJobAlreadyActive(stateEvent)} ")
        return dataChannelManager.isJobAlreadyActive(stateEvent)
    }

    fun getCurrentViewStateOrNew(): ViewState {
        val value = viewState.value?.let {
            it
        } ?: initNewViewState()
        return value
    }

    // update the viewstate. Observers (views) can then act on the (changed) data
    fun setViewState(viewState: ViewState) {
        _viewState.value = viewState
    }

    fun clearStateMessage(index: Int = 0) {
        dataChannelManager.clearStateMessage(index)
    }

    open fun cancelActiveJobs() {
        if (areAnyJobsActive()) {
            Log.d(TAG, "cancel active jobs: ${dataChannelManager.numActiveJobs.value ?: 0}")
            dataChannelManager.cancelJobs()
        }
    }

    abstract fun initNewViewState(): ViewState

}






