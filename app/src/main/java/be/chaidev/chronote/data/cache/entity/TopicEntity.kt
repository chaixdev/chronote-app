package be.chaidev.chronote.data.cache.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import be.chaidev.chronote.data.model.Note
import be.chaidev.chronote.data.model.Subject
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.data.model.Type
import java.time.Instant

@Entity(tableName = "topics")
data class TopicEntity(
    @PrimaryKey
    val id: String,
    @Embedded
    val subject: Subject,
    val tags: List<String>,
    val dateCreated: Instant,
    val dateModified: Instant
) {
    companion object {
        fun fromTopic(topic: Topic): TopicEntity {
            return TopicEntity(
                topic.id,
                topic.subject,
                topic.tags,
                topic.dateCreated,
                topic.dateCreated
            )
        }
    }
    fun toTopic(notes: List<Note>): Topic {
        return Topic(
            id,
            subject,
            tags,
            dateCreated,
            dateModified,
            notes
        )
    }
}