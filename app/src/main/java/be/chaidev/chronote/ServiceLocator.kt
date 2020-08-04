/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package be.chaidev.chronote

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import be.chaidev.chronote.data.AppDatabase
import be.chaidev.chronote.data.TopicLocalDataSource
import be.chaidev.chronote.navigation.AppNavigator
import be.chaidev.chronote.navigation.AppNavigatorImpl

import be.chaidev.chronote.util.DateFormatter

class ServiceLocator(applicationContext: Context) {

    private val topicsDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "chronote.db"
    ).build()

    val topicLocalDataSourceLocalDataSource = TopicLocalDataSource(topicsDatabase.topicDao())

    fun provideDateFormatter() = DateFormatter()

    fun provideNavigator(activity: FragmentActivity): AppNavigator {
        return AppNavigatorImpl(activity)
    }
}
