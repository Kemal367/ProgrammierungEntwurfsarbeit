package com.example.programmierungentwurfsarbeit.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri? {
    return try {
        val dir = File(context.cacheDir, "images").apply { mkdirs() }
        val file = File.createTempFile("img_", ".jpg", dir)
        FileOutputStream(file).use { out ->
            // Die quality muss nicht auf 100 gesetzt werden, weil
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }
        Uri.fromFile(file)
    } catch (_: Throwable) {
        // Im Fehlerfall: null zurückgeben (Caller kann entsprechend reagieren)
        null
    }
}
