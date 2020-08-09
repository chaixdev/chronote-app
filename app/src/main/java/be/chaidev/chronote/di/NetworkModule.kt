package be.chaidev.chronote.di

import be.chaidev.chronote.datasources.network.dto.NoteDto
import be.chaidev.chronote.datasources.network.retrofit.LiveDataCallAdapterFactory
import be.chaidev.chronote.datasources.network.retrofit.NoteDeserializer
import be.chaidev.chronote.datasources.network.retrofit.StreamarksApi
import be.chaidev.chronote.datasources.network.retrofit.SubjectDeserializer
import be.chaidev.chronote.model.Subject
import be.chaidev.chronote.util.Constants
import be.chaidev.chronote.util.Constants.STREAMARKS_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(NoteDto::class.java, NoteDeserializer())
            .registerTypeAdapter(Subject::class.java, SubjectDeserializer())
            .excludeFieldsWithoutExposeAnnotation()
            .create()

    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        client: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(STREAMARKS_URL)
            .client(client)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .callTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providesStreamarksApi(retrofit: Retrofit.Builder): StreamarksApi {
        return retrofit
            .build()
            .create(StreamarksApi::class.java)

    }
}