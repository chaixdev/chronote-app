package be.chaidev.chronote.data.network.retrofit

import be.chaidev.chronote.data.network.dto.TopicDto
import retrofit2.http.GET

interface StreamarksApi {

    @GET("topics")
    suspend fun getTopics(): List<TopicDto>


}