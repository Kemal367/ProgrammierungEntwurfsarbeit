package com.example.programmierungentwurfsarbeit.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.programmierungentwurfsarbeit.model.Inspiration
import com.example.programmierungentwurfsarbeit.model.mockDatenInspiration
import com.example.programmierungentwurfsarbeit.ui.splash.SplashScreen
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Composable
fun AppRoot() {
    var isReady by rememberSaveable { mutableStateOf(false) }
    var preloaded by remember { mutableStateOf<List<Inspiration>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            // Die Animation wird mindestens 3 sek abgespielt
            val minSplash = async { delay(3000) }
            // Daten werden geladen
            val loadData = async { preloaded = preloadInspiration() }
            // Beide Jobs abwarten, bevor es weitergeht.
            minSplash.await();
            loadData.await()
        } catch (t: Throwable) {
            Log.e("AppRoot", "Fehler beim Laden der Mockdaten. Eine Leere Liste wird übergeben", t)
            preloaded = emptyList() // Beim Fehler wird eine leere Liste übergeben
        } finally {
            // Nach Laden (oder Fehler) Animation beenden.
            isReady = true
        }
    }

    if (!isReady) {
        // Animation läuft, solange die Daten nicht vorhanden sind oder die Mindestzeit läuft.
        SplashScreen()
    } else {
        RootScreen(initialInspiration = preloaded)
    }
}

private suspend fun preloadInspiration(): List<Inspiration> {
    delay(300)
    return mockDatenInspiration()
}
