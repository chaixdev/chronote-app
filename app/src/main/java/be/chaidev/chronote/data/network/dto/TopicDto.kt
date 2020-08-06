package be.chaidev.chronote.data.network.dto

import be.chaidev.chronote.data.model.Subject
import be.chaidev.chronote.data.model.Topic
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.Instant

data class TopicDto(

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("subject")
    @Expose
    val subject: Subject,

    @SerializedName("tags")
    @Expose
    val tags: List<String>,

    @SerializedName("dateCreated")
    @Expose
    val dateCreated: String,

    @SerializedName("dateModified")
    @Expose
    val dateModified: String,


    @SerializedName("notes")
    @Expose
    val notes: List<NoteDto>

) {

    companion object {
        fun fromTopic(topic: TopicDto): TopicDto {

            return TopicDto(
                topic.id,
                topic.subject,
                topic.tags,
                topic.dateCreated,
                topic.dateModified,
                topic.notes
            )
        }
    }

    fun toTopic(): Topic {
        return Topic(
            id,
            subject,
            tags,
            Instant.parse(dateCreated),
            Instant.parse(dateModified),
            notes.map(NoteDto::toNote)
        )
    }
}