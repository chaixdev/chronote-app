package be.chaidev.chronote.data

import androidx.room.TypeConverter
import be.chaidev.chronote.data.model.Type
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.Instant


class Converters {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromInstant(value: Instant): String {
            return value.toString()
        }

        @TypeConverter
        @JvmStatic
        fun toInstant(value: String): Instant {
            return Instant.parse(value)
        }

        @TypeConverter
        @JvmStatic
        fun toList(listOfString: String?): List<String?>? {
            return Gson().fromJson(listOfString, object : TypeToken<List<String?>?>() {}.getType())
        }

        @TypeConverter
        @JvmStatic
        fun fromList(listOfString: List<String?>?): String? {
            return Gson().toJson(listOfString)
        }

        @TypeConverter
        @JvmStatic
        fun fromTypeEnum(value: Type): String {
            return value.toString()
        }

        @TypeConverter
        @JvmStatic
        fun toType(value: String): Type {
            return Type.valueOf(value)
        }
    }
}