package com.example.programmierungentwurfsarbeit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PunkteIndikator(count: Int, selectedIndex: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        repeat(count) { i ->
            // Aktive Farbe für den ausgewählten Punkt, sonst Outline-Farbe
            var color: Color
            if (i == selectedIndex) {
                color = MaterialTheme.colorScheme.primary
            } else {
                color = MaterialTheme.colorScheme.outline
            }

            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
            )

            // Abstand zwischen den Punkten (nicht nach dem letzten)
            if (i != count - 1) {
                Spacer(Modifier.width(10.dp))
            }
        }
    }
}
