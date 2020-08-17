package be.chaidev.chronote.repository

import be.chaidev.chronote.datasources.api.ApiResult
import be.chaidev.chronote.datasources.api.ApiResult.GenericError
import be.chaidev.chronote.datasources.api.ApiResult.Success
import be.chaidev.chronote.datasources.cache.CacheResult
import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.mvi.StateEvent
import be.chaidev.chronote.ui.mvi.UIComponentType
import be.chaidev.chronote.util.Constants
import be.chaidev.chronote.util.Constants.NETWORK_TIMEOUT
import be.chaidev.chronote.util.ErrorHandling.Companion.CACHE_ERROR_TIMEOUT
import be.chaidev.chronote.util.ErrorHandling.Companion.NETWORK_ERROR_TIMEOUT
import be.chaidev.chronote.util.ErrorHandling.Companion.UNKNOWN_ERROR
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException


/**
 * Reference: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 */
suspend fun <T> safeApiCall(

    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?
): ApiResult<T?> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            withTimeout(NETWORK_TIMEOUT) {
                Success(apiCall.invoke())
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is TimeoutCancellationException -> {
                    val code = 408 // timeout error code
                    GenericError(code, NETWORK_ERROR_TIMEOUT)
                }
                is IOException -> {
                    ApiResult.NetworkError
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    GenericError(
                        code,
                        errorResponse
                    )
                }
                else -> {
                    GenericError(
                        null,
                        UNKNOWN_ERROR
                    )
                }
            }
        }
    }
}

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend () -> T?
): CacheResult<T?> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            withTimeout(Constants.CACHE_TIMEOUT) {
                CacheResult.Success(cacheCall.invoke())
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is TimeoutCancellationException -> {
                    CacheResult.GenericError(CACHE_ERROR_TIMEOUT)
                }
                else -> {
                    CacheResult.GenericError(UNKNOWN_ERROR)
                }
            }
        }
    }
}


fun <ViewState> buildError(
    message: String,
    uiComponentType: UIComponentType,
    stateEvent: StateEvent
): DataState<ViewState> {
    return DataState.error(
        errorMessage = "${stateEvent.errorInfo()}\n\nReason: ${message}",
        stateEvent = stateEvent
    )

}

private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.toString()
    } catch (exception: Exception) {
        UNKNOWN_ERROR
    }
}
