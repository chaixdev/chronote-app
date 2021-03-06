package be.chaidev.chronote.di

import android.content.Context
import androidx.room.Room
import be.chaidev.chronote.datasources.cache.AppDatabase
import be.chaidev.chronote.datasources.cache.dao.TopicDao
import be.chaidev.chronote.util.Constants
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

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

}