package be.chaidev.chronote.data.network.retrofit

import androidx.lifecycle.LiveData
import be.chaidev.chronote.data.network.dto.TopicDto
import retrofit2.http.GET

interface StreamarksApi {

    @GET("topic/")
    suspend fun getTopics(): LiveData<List<TopicDto>>


}