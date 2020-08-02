package be.chaidev.chronote.model


data class Note (
    val id: String,
    val title: String,
    val body: String,
    val interval: Interval
)