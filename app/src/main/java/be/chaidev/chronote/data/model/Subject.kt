package be.chaidev.chronote.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Subject(

     val type: Type,
     val uri: String,
     val title: String,
     val duration: Long
):Parcelable