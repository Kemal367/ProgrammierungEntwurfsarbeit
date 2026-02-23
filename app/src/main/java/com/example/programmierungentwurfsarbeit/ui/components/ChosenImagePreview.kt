package com.example.programmierungentwurfsarbeit.ui.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ChosenImagePreview(
    pickedImageUri: Uri?,
    cameraBitmap: Bitmap?,
    imageRes: Int?
) {
    // Max-Höhe begrenzen
    // Bild wird kleiner
    val commonModifier = Modifier
        .fillMaxWidth()
        .heightIn(max = 140.dp)
        .clip(RoundedCornerShape(12.dp))

    when {

        pickedImageUri != null -> AsyncImage(
            model = pickedImageUri,
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = commonModifier
        )

        cameraBitmap != null -> Image(
            bitmap = cameraBitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = commonModifier
        )

        imageRes != null -> Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = commonModifier
        )
    }
}
