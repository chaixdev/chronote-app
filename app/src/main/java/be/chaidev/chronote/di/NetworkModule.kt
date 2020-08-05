package be.chaidev.chronote.di

import be.chaidev.chronote.data.model.Interval
import be.chaidev.chronote.data.model.Subject
import be.chaidev.chronote.data.network.dto.NoteDto
import be.chaidev.chronote.data.network.retrofit.IntervalDeserializer
import be.chaidev.chronote.data.network.retrofit.NoteDeserializer
import be.chaidev.chronote.data.network.retrofit.StreamarksApi
import be.chaidev.chronote.data.network.retrofit.SubjectDeserializer
import be.chaidev.chronote.util.STREAMARKS_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule{
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(NoteDto::class.java, NoteDeserializer())
            .registerTypeAdapter(Interval::class.java, IntervalDeserializer())
            .registerTypeAdapter(Subject::class.java, SubjectDeserializer())
            .excludeFieldsWithoutExposeAnnotation()
            .create()

    }

    @Singleton
    @Provides
    fun provideRetrofit(gson :Gson): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(STREAMARKS_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }


    @Singleton
    @Provides
    fun providesStreamarksApi(retrofit: Retrofit.Builder):StreamarksApi{
        return retrofit
            .build()
            .create(StreamarksApi::class.java)

    }
}