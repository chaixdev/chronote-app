package be.chaidev.chronote.datasources.cache.entity

import androidx.room.Embedded
import androidx.room.Relation
import be.chaidev.chronote.model.Topic

data class TopicWithNotes(
    @Embedded
    val topicEntity: TopicEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "topicId"
    )
    val notes: List<NoteEntity>
) {
    fun toTopic(): Topic {
        return topicEntity.toTopic(notes.map(NoteEntity::toNote))
    }
}