package be.chaidev.chronote.datasources.cache

import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.mvi.StateEvent

/**
 * methods in this class will take an api response object, take the statevent, wrap it in a datastate and return
 * **/
abstract class CacheResponseHandler<ViewState, Data>(
    private val response: CacheResult<Data?>,
    private val stateEvent: StateEvent
) {
    suspend fun getResult(): DataState<ViewState> {

        return when (response) {
            is CacheResult.GenericError -> {
                DataState.error<ViewState>(
                    errorMessage = "${stateEvent.errorInfo()}\n\nReason: ${response.errorMessage}",
                    stateEvent = stateEvent
                )
            }

            is CacheResult.Success -> {
                if (response.value == null) {
                    DataState.error(
                        errorMessage = "${stateEvent.errorInfo()}\n\nReason: Data is NULL.",
                        stateEvent = stateEvent
                    )
                } else {
                    handleSuccess(resultObj = response.value)
                }
            }

        }
    }

    abstract suspend fun handleSuccess(resultObj: Data): DataState<ViewState>

}