package be.chaidev.chronote.data.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.chaidev.chronote.data.model.Note

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
        fun fromNote(note:Note, topicId:String):NoteEntity{
            return NoteEntity(
                note.id,
                note.body,
                note.time,
                topicId
            )
        }
    }
}
