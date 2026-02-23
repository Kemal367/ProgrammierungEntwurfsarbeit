package com.example.programmierungentwurfsarbeit.ui.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.programmierungentwurfsarbeit.R

/**
 * Zeigt ein Bild für eine Aktivität an.
 *
 * Priorität der Quellen:
 * 1) Vorlage von den Aktivitäten
 * 2) Bild aus der Galerie oder von einer Datei (über eine URI erreichbar).
 * 3) Bild aus der Kamera (nur solange die App läuft/nicht neugestartet wurde).
 * 4) Falls nichts davon verfügbar ist: Logo der App (ohne Schriftzug).
 *
 * Anhand des Modifiers wird vorgegeben wie das Bild zugeschnitten werden soll.
 */
@Composable
fun ActivityImage(
    imageRes: Int?,           // Bild von Inspiration
    imageUri: Uri?,           // Bild, das als Datei oder aus der Galerie geladen wird
    cameraBitmap: Bitmap?,    // Bild aus Kamera
    contentDescription: String?,
    modifier: Modifier        // Vorgaben für Größe usw.
) {
    when {
        // 1) Bevorzugt ein eingebettetes Ressourcenbild (Vorlage)
        imageRes != null -> Image(
            painter = painterResource(imageRes),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )

        // 2) URI aus der Galerie
        imageUri != null -> AsyncImage(
            model = imageUri,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )

        // 3) Kamerabild
        cameraBitmap != null -> Image(
            bitmap = cameraBitmap.asImageBitmap(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )

        // 4) Letzter Fallback, wenn kein Bild vorhanden ist, wird das Logo genommen
        else -> Image(
            painter = painterResource(R.drawable.logo_without_caption),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    }
}
