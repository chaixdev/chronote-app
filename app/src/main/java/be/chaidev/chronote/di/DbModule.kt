package be.chaidev.chronote.di

import android.content.Context
import androidx.room.Room
import be.chaidev.chronote.data.cache.AppDatabase
import be.chaidev.chronote.data.cache.DataCache
import be.chaidev.chronote.data.cache.RoomDataCache
import be.chaidev.chronote.data.cache.dao.NoteDao
import be.chaidev.chronote.data.cache.dao.TopicDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DbModule {

    @Provides
    @Singleton
    fun provideTopicDao(db: AppDatabase): TopicDao {
        return db.topicDao()
    }

    @Provides
    @Singleton
    fun provideNoteDao(db: AppDatabase): NoteDao {
        return db.noteDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDataCache(topicDao: TopicDao, noteDao: NoteDao): DataCache {
        return RoomDataCache(topicDao, noteDao)
    }

}