package com.example.programmierungentwurfsarbeit.model

import com.example.programmierungentwurfsarbeit.R
import org.junit.Assert.assertEquals
import org.junit.Test

class KategorieAndBottomTabTest {

    @Test
    fun kategorie_containsExpectedLabels() {
        assertEquals("meditation", Kategorie.Meditation.label)
        assertEquals("natur", Kategorie.Natur.label)
        assertEquals("digitalDetox", Kategorie.DigitalDetox.label)
        assertEquals("sport", Kategorie.Sport.label)
    }

    @Test
    fun bottomTab_containsExpectedStringResources() {
        assertEquals(R.string.inspiration, BottomTab.Inspiration.labelRes)
        assertEquals(R.string.aktivitaeten, BottomTab.Activities.labelRes)
        assertEquals(R.string.impressum, BottomTab.Impressum.labelRes)
    }
}
