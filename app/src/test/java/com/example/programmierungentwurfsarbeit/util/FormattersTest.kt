package com.example.programmierungentwurfsarbeit.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale
import java.util.TimeZone

class FormattersTest {

    @Test
    fun formatTimestamp_formatsExpectedPattern() {
        val previousLocale = Locale.getDefault()
        val previousTimeZone = TimeZone.getDefault()

        try {
            Locale.setDefault(Locale.GERMANY)
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

            val formatted = formatTimestamp(0L)

            assertEquals("01.01.1970 00:00", formatted)
        } finally {
            Locale.setDefault(previousLocale)
            TimeZone.setDefault(previousTimeZone)
        }
    }
}
