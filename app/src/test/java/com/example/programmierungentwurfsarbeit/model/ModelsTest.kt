package com.example.programmierungentwurfsarbeit.model

import com.example.programmierungentwurfsarbeit.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ModelsTest {

    @Test
    fun mockDatenInspiration_returnsExpectedSeedData() {
        val result = mockDatenInspiration()

        assertEquals(5, result.size)
        assertEquals("4-7-8 Atemtechnik", result.first().title)
        assertEquals(Kategorie.Meditation, result.first().category)
        assertEquals(R.drawable.meditating, result.first().imageRes)
    }

    @Test
    fun toActivity_mapsFieldsAndSetsDuration() {
        val inspiration = Inspiration(
            title = "Spaziergang",
            category = Kategorie.Natur,
            imageRes = R.drawable.go_for_a_walk,
            description = "Kurzer Spaziergang draußen"
        )

        val activity = inspiration.toActivity(durationMinutes = 20)

        assertEquals(inspiration.title, activity.title)
        assertEquals(inspiration.category, activity.category)
        assertEquals(inspiration.description, activity.description)
        assertEquals(inspiration.imageRes, activity.imageRes)
        assertEquals(20, activity.durationMinutes)
        assertNull(activity.imageUri)
        assertNull(activity.cameraPreview)
    }
}
