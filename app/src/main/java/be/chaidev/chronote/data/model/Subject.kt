package be.chaidev.chronote.data.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Subject(
     val type: Type,
     val uri: String,
     val title: String,
     val duration: Long
):Parcelable