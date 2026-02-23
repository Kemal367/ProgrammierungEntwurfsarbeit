package com.example.programmierungentwurfsarbeit.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.programmierungentwurfsarbeit.R
import com.example.programmierungentwurfsarbeit.model.Inspiration
import com.example.programmierungentwurfsarbeit.model.UserActivity
import com.example.programmierungentwurfsarbeit.model.toActivity
import com.example.programmierungentwurfsarbeit.ui.components.ActivityCard
import com.example.programmierungentwurfsarbeit.ui.components.DurationDialog
import com.example.programmierungentwurfsarbeit.ui.components.PagerSection
import com.example.programmierungentwurfsarbeit.ui.components.SectionTitle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    items: List<Inspiration>,                 // Liste der Inspirationskarten
    onAddFromInspiration: (UserActivity) -> Unit // gewählte Inspiration als Aktivität übernehmen
) {
    // merken der letzten Karte
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { items.size })

    // Merkt sich, die Inspiration-Karte für die Dauer-Auswahl
    var pickFor by remember { mutableStateOf<Inspiration?>(null) }

    // Gemeinsamer Pager-Container (Titel + Dots + HorizontalPager)
    PagerSection(
        title = { SectionTitle(R.string.inspiration) },
        pagerState = pagerState,
        pageCount = items.size,
        modifier = modifier
    ) { page ->
        val item = items[page]
        // Einzelne Karte im Pager –  identisch mit Aktivitäten
        ActivityCard(
            title = item.title,
            categoryLabel = item.category.label,
            description = item.description,
            imageContent = { m ->
                Image(
                    painter = painterResource(item.imageRes),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = m
                )
            },
            highlighted = pagerState.currentPage == page,  // Border/Highlight auf aktueller Seite
            onClick = { pickFor = item },                  // Klick öffnet Dauer-Dialog
            category = item.category                       // entscheidet welche Farbe bei Badge-/Border-Farben genutzt wird
        )
    }

    // Dialog zur Eingabe der Dauer. Wenn der User die Dauer bestätigt wird eine UserActivity erstellt
    pickFor?.let { chosen ->
        DurationDialog(
            titleText = "Dauer festlegen – ${chosen.title}",
            onConfirm = { minutes ->
                onAddFromInspiration(chosen.toActivity(minutes))
                pickFor = null
            },
            onDismiss = { pickFor = null }
        )
    }
}
