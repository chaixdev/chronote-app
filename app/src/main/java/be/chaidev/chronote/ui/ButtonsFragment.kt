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

package be.chaidev.chronote.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import be.chaidev.chronote.ChronoteApp
import be.chaidev.chronote.R
import be.chaidev.chronote.data.TopicLocalDataSource
import be.chaidev.chronote.navigation.AppNavigator
import be.chaidev.chronote.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment that displays buttons whose interactions are recorded.
 */
@AndroidEntryPoint
class ButtonsFragment : Fragment() {

    @Inject lateinit var logger: TopicLocalDataSource
    @Inject lateinit var navigator: AppNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_buttons, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.create).setOnClickListener {
            logger.addTopic("btn1")
        }

        view.findViewById<Button>(R.id.viewAll).setOnClickListener {
            logger.addTopic("btn2")
        }

        view.findViewById<Button>(R.id.all_topics).setOnClickListener {
            navigator.navigateTo(Screens.LOGS)
        }

        view.findViewById<Button>(R.id.to_chrono).setOnClickListener {
            navigator.navigateTo(Screens.CHRONO)
        }

        view.findViewById<Button>(R.id.delete_topics).setOnClickListener {
            logger.removeTopics()
        }

    }
}
