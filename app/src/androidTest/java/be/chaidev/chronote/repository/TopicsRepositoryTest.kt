package be.chaidev.chronote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import be.chaidev.chronote.datasources.network.ApiErrorResponse
import be.chaidev.chronote.datasources.network.GenericApiResponse
import be.chaidev.chronote.datasources.network.dto.TopicDto
import be.chaidev.chronote.datasources.network.retrofit.StreamarksApi
import be.chaidev.chronote.di.NetworkModule
import be.chaidev.chronote.testutil.getOrAwaitValue
import org.junit.*

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
    fun test1() {
        println("test")
        val topicsLD: LiveData<GenericApiResponse<List<TopicDto>>> = apiService.getTopics()
        val topics = topicsLD.getOrAwaitValue()
        Assert.assertTrue(topics.javaClass.name == ApiErrorResponse::class.qualifiedName)
    }

    @After
    fun teardown() {
    }
}