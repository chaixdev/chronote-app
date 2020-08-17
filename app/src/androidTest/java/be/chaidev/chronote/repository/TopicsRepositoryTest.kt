package be.chaidev.chronote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import be.chaidev.chronote.datasources.api.dto.TopicDto
import be.chaidev.chronote.datasources.api.retrofit.StreamarksApi
import be.chaidev.chronote.di.NetworkModule
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TopicsRepositoryTest {

    private lateinit var apiService: StreamarksApi

    @Before
    fun setup() {

        apiService = NetworkModule.providesStreamarksApi(
            NetworkModule.provideRetrofit(
                NetworkModule.provideGsonBuilder(),
                NetworkModule.provideOkHttpClient()
            )
        )
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun testDeserialisationThrowsNoExceptions() {

        runBlocking {
            launch {
                val topics: List<TopicDto> = apiService.getTopics()

                Assert.assertEquals("size didnt match testJson", topics.size, 11)


            }
        }
    }


    @Test
    fun testMappersToAndFrom() {
        runBlocking {
            println("test")

            runBlocking {
                launch {
                    val topics: List<TopicDto> = apiService.getTopics()
                    val dtoToDomainMap = topics.map { topicDto -> topicDto.toTopic() }
                    val domainToDtoMap = dtoToDomainMap.map { TopicDto.fromTopic(it) }

                    Assert.assertEquals(topics, domainToDtoMap)


                }
            }


        }
    }
}