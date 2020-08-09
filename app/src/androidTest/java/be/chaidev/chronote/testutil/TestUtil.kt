package be.chaidev.chronote.testutil

import be.chaidev.chronote.datasources.cache.entity.TopicEntity
import be.chaidev.chronote.model.Note
import be.chaidev.chronote.model.Subject
import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.model.Type
import java.time.Instant

/*
 * Copyright (C) 2017 The Android Open Source Project
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
object TestUtil {

    fun createTopic(): Topic {

        val notes = listOf(
            Note("note1", "this is a testnote", 1000),
            Note("note2", "this is a testnote", 2000),
            Note("note3", "this is a testnote", 3000)
        )
        return Topic(
            "uuid1",
            Subject(Type.YOUTUBE, "file://file", "title", 10000),
            listOf("Movie", "review", "critique"),
            Instant.now(),
            Instant.now(),
            notes
        )
    }

    fun createTopicEntity() = TopicEntity.fromTopic(createTopic())


}