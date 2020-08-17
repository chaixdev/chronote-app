package be.chaidev.chronote.datasource.network

import be.chaidev.chronote.datasources.api.dto.GenericResponse
import be.chaidev.chronote.datasources.api.dto.TopicDto
import be.chaidev.chronote.datasources.api.retrofit.StreamarksApi
import be.chaidev.chronote.testutil.FileReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay

class TestStreamarksApi : StreamarksApi {

    val topicsJsonResource = "api_get_topics_success.json"
    var networkDelay = 0L


    override suspend fun getTopics(): List<TopicDto> {
        val topicsJsonString: String = FileReader.readStringFromFile(topicsJsonResource)
        val topics = Gson().fromJson<List<TopicDto>>(
            topicsJsonString,
            object : TypeToken<List<TopicDto>>() {}.type
        )
        delay(networkDelay)

        return topics


    }

    override suspend fun deleteTopic(id: String): GenericResponse {
        TODO("Not yet implemented")
    }

    override suspend fun updateTopic(dto: TopicDto): TopicDto {
        TODO("Not yet implemented")
    }

}