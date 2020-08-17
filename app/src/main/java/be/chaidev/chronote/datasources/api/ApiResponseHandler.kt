package be.chaidev.chronote.datasources.api

import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.mvi.StateEvent
import be.chaidev.chronote.util.ErrorHandling.Companion.NETWORK_ERROR
import be.chaidev.chronote.util.ErrorHandling.Companion.UNKNOWN_ERROR

/**
 * methods in this class will take an api response object, take the statevent, wrap it in a datastate and return
 * **/

abstract class ApiResponseHandler<ViewState, Data>(
    response: ApiResult<Data?>,
    stateEvent: StateEvent
) {
    val result: DataState<ViewState> = when (response) {

        is ApiResult.GenericError -> {
            DataState.error(
                errorMessage = stateEvent.errorInfo()
                        + "\n\nReason: " + response.errorMessage,
                stateEvent = stateEvent
            )
        }

        is ApiResult.NetworkError -> {
            DataState.error(
                errorMessage = stateEvent.errorInfo()
                        + "\n\nReason: " + NETWORK_ERROR,
                stateEvent = stateEvent
            )
        }

        is ApiResult.Success -> {
            if (response.value == null) {
                DataState.error(
                    errorMessage = stateEvent.errorInfo()
                            + "\n\nReason: " + UNKNOWN_ERROR,
                    stateEvent = stateEvent
                )
            } else {
                handleSuccess(resultObj = response.value)
            }
        }

    }

    abstract fun handleSuccess(resultObj: Data): DataState<ViewState>

}