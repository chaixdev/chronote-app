package be.chaidev.chronote.datasources.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.chaidev.chronote.model.Note

@Entity(tableName = "notes")
data class NoteEntity (
    @PrimaryKey(autoGenerate = false)
    val noteId: String,
    val body: String,
    val time:Long,
    val topicId: String

) {
    fun toNote(): Note{
        return Note(noteId,body,time)
    }

    companion object {
        @JvmStatic
        fun fromNote(note: Note, topicId: String): NoteEntity {
            return NoteEntity(
                note.id,
                note.body,
                note.time,
                topicId
            )
        }

        @JvmStatic
        fun fromNotes(notes: List<Note>, topicId: String): List<NoteEntity> {
            return notes.map { note -> fromNote(note, topicId) }
        }
    }
}
