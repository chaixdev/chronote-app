package be.chaidev.chronote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import be.chaidev.chronote.datasources.network.ApiEmptyResponse
import be.chaidev.chronote.datasources.network.ApiErrorResponse
import be.chaidev.chronote.datasources.network.ApiSuccessResponse
import be.chaidev.chronote.datasources.network.GenericApiResponse
import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.mvi.Response
import be.chaidev.chronote.ui.mvi.ResponseType
import be.chaidev.chronote.util.Constants.NETWORK_TIMEOUT
import be.chaidev.chronote.util.Constants.TAG
import be.chaidev.chronote.util.Constants.TESTING_CACHE_DELAY
import be.chaidev.chronote.util.Constants.TESTING_NETWORK_DELAY
import be.chaidev.chronote.util.ErrorHandling
import be.chaidev.chronote.util.ErrorHandling.Companion.ERROR_CHECK_NETWORK_CONNECTION
import be.chaidev.chronote.util.ErrorHandling.Companion.ERROR_UNKNOWN
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

abstract class NetworkBoundResource<NetworkDto, Model, ViewStateType>
    (
    isNetworkAvailable: Boolean, // is there a network connection?
    isNetworkRequest: Boolean, // is this a network request?
    shouldCancelIfNoInternet: Boolean, // should this job be cancelled if there is no network?
    shouldLoadFromCache: Boolean // should the cached data be loaded?
) {
    protected val result = MediatorLiveData<DataState<ViewStateType>>()
    protected lateinit var job: CompletableJob
    protected lateinit var coroutineScope: CoroutineScope

    init {
        setJob(initNewJob())
        setValue(DataState.loading(isLoading = true, cachedData = null))

        if(shouldLoadFromCache){
            // view cache to start
            val dbSource = loadFromCache()
            result.addSource(dbSource){
                result.removeSource(dbSource)
                setValue(DataState.loading(isLoading = true, cachedData = it))
            }
        }

        if(isNetworkRequest){
            if(isNetworkAvailable){
                doNetworkRequest()
            }
            else{
                if(shouldCancelIfNoInternet){
                    onErrorReturn(
                        ErrorHandling.UNABLE_TODO_OPERATION_WO_INTERNET,
                        shouldUseDialog = true,
                        shouldUseToast = false)
                }
                else{
                    doCacheRequest()
                }
            }
        }
        else{
            doCacheRequest()
        }
    }

    fun doCacheRequest(){
        coroutineScope.launch {
            delay(TESTING_CACHE_DELAY)
            // View data from cache only and return
            createCacheRequestAndReturn()
        }
    }

    fun doNetworkRequest(){
        coroutineScope.launch {

            // simulate a network delay for testing
            delay(TESTING_NETWORK_DELAY)

            withContext(Main){

                // make network call
                val apiResponse = createCall()
                result.addSource(apiResponse){ response ->
                    result.removeSource(apiResponse)

                    coroutineScope.launch {
                        handleNetworkCall(response)
                    }
                }
            }
        }

        GlobalScope.launch(IO){
            delay(NETWORK_TIMEOUT)

            if(!job.isCompleted){
                Log.e(TAG, "NetworkBoundResource: JOB NETWORK TIMEOUT." )
                job.cancel(CancellationException(ErrorHandling.UNABLE_TO_RESOLVE_HOST))
            }
        }
    }

    suspend fun handleNetworkCall(response: GenericApiResponse<NetworkDto>){

        when(response){
            is ApiSuccessResponse ->{
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse ->{
                Log.e(TAG, "NetworkBoundResource: ${response.errorMessage}")
                onErrorReturn(response.errorMessage, true, false)
            }
            is ApiEmptyResponse ->{
                Log.e(TAG, "NetworkBoundResource: Request returned NOTHING (HTTP 204).")
                onErrorReturn("HTTP 204. Returned NOTHING.", true, false)
            }
        }
    }

    fun onCompleteJob(dataState: DataState<ViewStateType>){
        GlobalScope.launch(Main) {
            job.complete()
            setValue(dataState)
        }
    }

    fun onErrorReturn(errorMessage: String?, shouldUseDialog: Boolean, shouldUseToast: Boolean){
        var msg = errorMessage
        var useDialog = shouldUseDialog
        var responseType: ResponseType = ResponseType.None()
        if(msg == null){
            msg = ERROR_UNKNOWN
        }
        else if(ErrorHandling.isNetworkError(msg)){
            msg = ERROR_CHECK_NETWORK_CONNECTION
            useDialog = false
        }
        if(shouldUseToast){
            responseType = ResponseType.Toast()
        }
        if(useDialog){
            responseType = ResponseType.Dialog()
        }

        onCompleteJob(DataState.error(Response(msg, responseType)))
    }

    fun setValue(dataState: DataState<ViewStateType>){
        result.value = dataState
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun initNewJob(): Job{
        Log.d(TAG, "initNewJob: called.")
        job = Job() // create new job
        job.invokeOnCompletion(onCancelling = true, invokeImmediately = true, handler = object: CompletionHandler{
            override fun invoke(cause: Throwable?) {
                if(job.isCancelled){
                    Log.e(TAG, "NetworkBoundResource: Job has been cancelled.")
                    cause?.let{
                        onErrorReturn(it.message, false, true)
                    }?: onErrorReturn("Unknown error.", false, true)
                }
                else if(job.isCompleted){
                    Log.e(TAG, "NetworkBoundResource: Job has been completed.")
                    // Do nothing? Should be handled already
                }
            }
        })
        coroutineScope = CoroutineScope(IO + job)
        return job
    }

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

    open suspend fun createCacheRequestAndReturn() {}

    abstract suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<NetworkDto>)

    abstract fun createCall(): LiveData<GenericApiResponse<NetworkDto>>

    abstract fun loadFromCache(): LiveData<ViewStateType>

    abstract suspend fun updateLocalDb(cacheObject: Model?)

    abstract fun setJob(job: Job)

}















