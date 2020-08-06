package be.chaidev.chronote.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

/**
 * String formatter for the log dates.
 */
class DateTimeUtils @Inject constructor() {

    companion object {
        private val formatter = DateTimeFormatter

            .ofLocalizedDateTime(FormatStyle.SHORT)
            .withZone(ZoneId.systemDefault())

        fun formatInstant(instant: Instant): String {

            return formatter.format(instant)
        }

        fun getNow():String{
            return DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        }
    }


}
