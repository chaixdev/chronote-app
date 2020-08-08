package be.chaidev.chronote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(

    val id: String,
    val body: String,
    val time: Long
) : Parcelable
