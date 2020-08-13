package be.chaidev.chronote.datasources.api.retrofit

import be.chaidev.chronote.datasources.api.dto.GenericResponse
import be.chaidev.chronote.datasources.api.dto.TopicDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

import retrofit2.http.Path

interface StreamarksApi {

    @GET("topic/")
    suspend fun getTopics(): List<TopicDto>

    @DELETE("topic/{id}")
    suspend fun deleteTopic(@Path("id") id: String): GenericResponse

    @PUT("topic/{id}")
    suspend fun updateTopic(@Path("id") dto: TopicDto): TopicDto

}