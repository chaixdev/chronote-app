package be.chaidev.chronote.data.network.retrofit

import androidx.lifecycle.LiveData
import be.chaidev.chronote.data.network.GenericApiResponse
import be.chaidev.chronote.data.network.dto.TopicDto
import retrofit2.http.GET

interface StreamarksApi {

    @GET("topic/")
    fun getTopics(): LiveData<GenericApiResponse<List<TopicDto>>>


}