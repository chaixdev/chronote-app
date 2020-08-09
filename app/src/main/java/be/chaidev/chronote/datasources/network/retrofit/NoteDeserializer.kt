package be.chaidev.chronote.datasources.network.retrofit

import be.chaidev.chronote.datasources.network.dto.NoteDto
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class NoteDeserializer: JsonDeserializer<NoteDto> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): NoteDto {

        return Gson().fromJson(json,NoteDto::class.java)
    }
}