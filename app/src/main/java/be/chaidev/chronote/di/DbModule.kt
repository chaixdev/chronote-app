package be.chaidev.chronote.di

import android.content.Context
import androidx.room.Room
import be.chaidev.chronote.data.AppDatabase
import be.chaidev.chronote.data.dao.TopicDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
object DbModule {

    @Provides
    fun provideTopicDao(db: AppDatabase): TopicDao {
        return db.topicDao()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext context:Context):AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "chronote.db"
        ).build()
    }

}