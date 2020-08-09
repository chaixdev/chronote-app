package be.chaidev.chronote.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.util.Constants.TAG

abstract class AbstractViewModel<StateEvent, ViewState> : ViewModel()
{

    protected val _stateEvent: MutableLiveData<StateEvent> = MutableLiveData()
    protected val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewState: LiveData<ViewState>
        get() = _viewState

    val dataState: LiveData<DataState<ViewState>> = Transformations
        .switchMap(_stateEvent){stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }
        }

    fun setStateEvent(event: StateEvent){
        _stateEvent.value = event
    }

    fun getCurrentViewStateOrNew(): ViewState{
        Log.d(TAG, "getCurrentViewStateOrNew")
        val value = viewState.value?.let{
            it
        }?: initNewViewState()
        return value
    }

    fun setViewState(viewState: ViewState){
        _viewState.value = viewState
    }

    abstract fun handleStateEvent(stateEvent: StateEvent): LiveData<DataState<ViewState>>

    abstract fun initNewViewState(): ViewState

}
