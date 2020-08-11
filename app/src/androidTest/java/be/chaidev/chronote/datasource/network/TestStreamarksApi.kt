package be.chaidev.chronote.datasource.network

import androidx.lifecycle.LiveData
import be.chaidev.chronote.datasources.network.GenericApiResponse
import be.chaidev.chronote.datasources.network.dto.GenericResponse
import be.chaidev.chronote.datasources.network.dto.TopicDto
import be.chaidev.chronote.datasources.network.retrofit.StreamarksApi
import be.chaidev.chronote.testutil.FileReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay

class TestStreamarksApi : StreamarksApi {

    val topicsJsonResource = "api_get_topics_success.json"
    var networkDelay = 0L


    override suspend fun getTopics(): GenericApiResponse<List<TopicDto>>>
    {
        val topicsJsonString: String = FileReader.readStringFromFile(topicsJsonResource)
        val topics = Gson().fromJson<List<TopicDto>>(
            topicsJsonString,
            object : TypeToken<List<TopicDto>>() {}.type
        )
        delay(networkDelay)

        return topics.asLi

    }

    override fun deleteTopic(id: String): LiveData<GenericApiResponse<GenericResponse>> {
        TODO("Not yet implemented")
    }
}