package be.chaidev.chronote.data.cache.entity

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
    val topicId: String,
    val subjectTitle: String,
    val subjectUri: String,
    val subjectType: String,
    val subjectDuration: Long,
    val tags: String,
    val dateCreated: String,
    val dateModified: String

) {
    companion object {
        fun fromTopic(topic: Topic): TopicEntity {
            return TopicEntity(
                topic.id,
                topic.subject.title,
                topic.subject.uri,
                topic.subject.type.name,
                topic.subject.duration,
                topic.tags.joinToString(";"),
                topic.dateCreated.toString(),
                topic.dateModified.toString()
            )
        }
    }

    fun toTopic(notes:List<Note>): Topic {
        val subject = Subject(
            Type.valueOf(subjectType),
            subjectUri,
            subjectTitle,
            subjectDuration
        )
        return Topic(
            topicId,
            subject,
            tags.split(";"),
            Instant.parse(dateCreated),
            Instant.parse(dateModified),
            notes
        )
    }
}