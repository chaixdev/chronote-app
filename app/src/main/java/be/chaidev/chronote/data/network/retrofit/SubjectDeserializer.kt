package be.chaidev.chronote.data.network.retrofit

import be.chaidev.chronote.data.model.Subject
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.data.network.dto.NoteDto
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class SubjectDeserializer: JsonDeserializer<Subject> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Subject {

        return Gson().fromJson(json,Subject::class.java)
    }
}