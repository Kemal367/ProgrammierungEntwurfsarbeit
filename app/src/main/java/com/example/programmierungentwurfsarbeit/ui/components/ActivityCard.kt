package com.example.programmierungentwurfsarbeit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.programmierungentwurfsarbeit.model.Kategorie
import com.example.programmierungentwurfsarbeit.ui.theme.CardWhite
import com.example.programmierungentwurfsarbeit.ui.theme.CategoryDigitalDetoxAccent
import com.example.programmierungentwurfsarbeit.ui.theme.CategoryMeditationAccent
import com.example.programmierungentwurfsarbeit.ui.theme.CategoryNaturAccent
import com.example.programmierungentwurfsarbeit.ui.theme.CategorySportAccent

/**
 * Einheitliche Karte (Inspiration-/Aktivitäten-Layout)
 */
@Composable
fun ActivityCard(
    title: String,                         // Überschrift der Karte
    categoryLabel: String,                 // Text der Kategorie (z. B. "#meditation")
    description: String,                   // Beschreibung unter dem Bild
    imageContent: @Composable (Modifier) -> Unit, //  Bild
    highlighted: Boolean,                  // sichtbaren Rahmen um die Karte
    rightChip: String? = null,             // min anzeige für Aktivitäten
    footerRight: String? = null,           // timestamp für Aktivitäten
    onClick: (() -> Unit)? = null,         // gesamter Kartenklick (optional)

    category: Kategorie? = null,           // echte Kategorie für Farbakzente (Border/Chips)
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    val shape = RoundedCornerShape(20.dp)

    // Farbe aus Kategorie ableiten; Fallbacks wenn keine Kategorie übergeben wurde
    val chipBg: Color =
        category?.accentColor() ?: MaterialTheme.colorScheme.primary.copy(alpha = .12f)
    val borderClr: Color =
        category?.accentColor() ?: MaterialTheme.colorScheme.primary.copy(alpha = .6f)

    // Sichtbarer, dicker Rahmen nur im "highlighted"-Zustand
    val border: BorderStroke? = if (highlighted) BorderStroke(5.dp, borderClr) else null

    val cardColors = CardDefaults.cardColors(containerColor = CardWhite)

    // Außen-Layout der Karte
    val outerModifier = Modifier
        .fillMaxSize()
        .padding(4.dp)

    @Composable
    fun CardContent() {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Titel
            Text(
                title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                color = textColor
            )

            Spacer(Modifier.height(8.dp))

            // Kategorie
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                // Kategorie-Box abgerundet
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(chipBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        "#$categoryLabel",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = textColor
                    )
                }
                Spacer(Modifier.weight(1f))
                // min für aktivitäten
                rightChip?.let {
                    Text(
                        it,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
                        color = textColor
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Bildbereich: 80% der Kartenhöhe
            imageContent(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(Modifier.height(12.dp))

            // Beschreibungstext
            Text(
                description,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )

            // Timestamp
            footerRight?.let {
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(chipBg) // gleiche Hintergrundfarbe wie Kategorie-Badge
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            it,
                            style = MaterialTheme.typography.labelSmall,
                            color = textColor
                        )
                    }
                }
            }
        }
    }

    if (onClick != null) {
        Card(
            onClick = onClick,
            shape = shape,
            border = border,
            colors = cardColors,
            modifier = outerModifier
        ) { CardContent() }
    } else {
        Card(
            shape = shape,
            border = border,
            colors = cardColors,
            modifier = outerModifier
        ) { CardContent() }
    }
}

// Farben je Kategorie
private fun Kategorie.accentColor(): Color = when (this) {
    Kategorie.Meditation -> CategoryMeditationAccent
    Kategorie.Natur -> CategoryNaturAccent
    Kategorie.DigitalDetox -> CategoryDigitalDetoxAccent
    Kategorie.Sport -> CategorySportAccent
}
