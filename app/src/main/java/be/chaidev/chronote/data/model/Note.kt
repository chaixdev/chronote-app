package be.chaidev.chronote.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note (

    val id: String,
    val body: String,
    val time: Long
):Parcelable{
}
