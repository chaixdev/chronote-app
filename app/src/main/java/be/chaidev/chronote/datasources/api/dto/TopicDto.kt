package be.chaidev.chronote.datasources.api.dto

import be.chaidev.chronote.model.Note
import be.chaidev.chronote.model.Subject
import be.chaidev.chronote.model.Topic
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
        fun fromTopic(topic: Topic): TopicDto {

            return TopicDto(
                topic.id,
                topic.subject,
                topic.tags,
                topic.dateCreated.toString(),
                topic.dateModified.toString(),
                topic.notes.map(NoteDto.Companion::fromNote)
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

    fun parsedNotes(): List<Note> {
        return notes.map { noteDto -> noteDto.toNote() }
    }
}