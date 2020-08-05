package be.chaidev.chronote.data.network.retrofit

import be.chaidev.chronote.data.model.Interval
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.data.network.dto.NoteDto
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class IntervalDeserializer: JsonDeserializer<Interval> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Interval {

        val note = json?.asJsonObject?.get("notes")

        return Gson().fromJson(note,Interval::class.java)
    }
}