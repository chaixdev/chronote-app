package be.chaidev.chronote.model


data class Topic(
    val subject: Subject,
    val tag: List<String>,
    val notes: List<Note>,
    val dateCreated: String,
    val dateModified: String,
    val folderPath: List<String>
)