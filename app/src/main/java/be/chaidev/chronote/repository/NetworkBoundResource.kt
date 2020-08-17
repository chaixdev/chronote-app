package be.chaidev.chronote.repository

import be.chaidev.chronote.datasources.api.ApiResult.*
import be.chaidev.chronote.datasources.cache.CacheResponseHandler
import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.mvi.StateEvent
import be.chaidev.chronote.ui.mvi.UIComponentType
import be.chaidev.chronote.util.ErrorHandling.Companion.NETWORK_ERROR
import be.chaidev.chronote.util.ErrorHandling.Companion.UNKNOWN_ERROR
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@FlowPreview
abstract class NetworkBoundResource<NetworkObj, CacheObj, ViewState>
constructor(
    private val dispatcher: CoroutineDispatcher,
    private val stateEvent: StateEvent,
    private val apiCall: suspend () -> NetworkObj?,
    private val cacheCall: suspend () -> CacheObj?
) {

    private val TAG: String = "AppDebug"

    val result: Flow<DataState<ViewState>> = flow {

        // ****** STEP 1: return what' s already in the cache
        emit(returnCache(markJobComplete = false))

        // ****** STEP 2: make the network call and save to cache
        val apiResult = safeApiCall(dispatcher) { apiCall }

        when (apiResult) {
            is GenericError -> {
                emit(
                    buildError(
                        apiResult.errorMessage?.let { it } ?: UNKNOWN_ERROR,
                        UIComponentType.Dialog(),
                        stateEvent
                    )
                )
            }

            is NetworkError -> {
                emit(
                    buildError(
                        NETWORK_ERROR,
                        UIComponentType.Dialog(),
                        stateEvent
                    )
                )
            }

            is Success -> {
                if (apiResult.value?.invoke() == null) {
                    emit(
                        buildError(
                            UNKNOWN_ERROR,
                            UIComponentType.Dialog(),
                            stateEvent
                        )
                    )
                } else {
                    // finaly the successful response
                    updateCache(apiResult.value.invoke() as NetworkObj)
                }
            }
        }

        // ****** STEP 3: query the cache and mark job complete
        emit(returnCache(markJobComplete = true))
    }

    private suspend fun returnCache(markJobComplete: Boolean): DataState<ViewState> {

        val cacheResult = safeCacheCall(dispatcher) { cacheCall.invoke() }

        var jobCompleteMarker: StateEvent = object : StateEvent {
            override fun errorInfo(): String = "Something went wrong while fetching from cache"
        }
        if (markJobComplete) {
            jobCompleteMarker = stateEvent
        }

        return object : CacheResponseHandler<ViewState, CacheObj>(
            response = cacheResult,
            stateEvent = jobCompleteMarker
        ) {
            override suspend fun handleSuccess(resultObj: CacheObj): DataState<ViewState> {
                return handleCacheSuccess(resultObj)
            }
        }.getResult()

    }

    abstract suspend fun updateCache(networkObject: NetworkObj)

    abstract fun handleCacheSuccess(resultObj: CacheObj): DataState<ViewState> // make sure to return null for stateEvent


}











