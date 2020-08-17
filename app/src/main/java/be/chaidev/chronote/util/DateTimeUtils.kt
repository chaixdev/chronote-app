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

        fun getNow(): String {
            return DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        }

        fun readableSeconds(milliseconds: Long): String {

            val seconds = (milliseconds / 1000) % 60
            val minutes = (milliseconds / (1000 * 60) % 60)
            val hours = (milliseconds / (1000 * 60 * 60) % 24)

            return String.format("%02d:%02d", minutes, seconds)
        }
    }
}
