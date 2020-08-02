package be.chaidev.chronote.model

import java.time.Duration

data class Subject (
    val type: Type,
    val uri: String,
    val subjectTitle: String,
    val duration: Duration
)