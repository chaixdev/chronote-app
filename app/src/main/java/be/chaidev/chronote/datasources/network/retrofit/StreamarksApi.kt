package be.chaidev.chronote.datasources.network.retrofit

import androidx.lifecycle.LiveData
import be.chaidev.chronote.datasources.network.GenericApiResponse
import be.chaidev.chronote.datasources.network.dto.GenericResponse
import be.chaidev.chronote.datasources.network.dto.TopicDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface StreamarksApi {

    @GET("topic/")
    fun getTopics(): LiveData<GenericApiResponse<List<TopicDto>>>

    @DELETE("topic/{id}")
    fun deleteTopic(@Path("id") id: String): LiveData<GenericApiResponse<GenericResponse>>


}