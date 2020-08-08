package be.chaidev.chronote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.Instant

@Parcelize
data class Topic(

    val id: String,
    val subject: Subject,
    val tags: List<String>,
    val dateCreated: Instant,
    val dateModified: Instant,
    val notes: List<Note>

) : Parcelable