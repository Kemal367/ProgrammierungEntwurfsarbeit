package com.example.programmierungentwurfsarbeit.util

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream


fun copyUriToCache(context: Context, source: Uri): Uri? = try {
    val dir = File(context.cacheDir, "images").apply { mkdirs() }
    val file = File.createTempFile(
        "img_",
        ".jpg",
        dir
    ) // z.B. file:///data/user/0/<pkg>/cache/images/img_xxx.jpg
    context.contentResolver.openInputStream(source).use { input ->
        FileOutputStream(file).use { output ->
            if (input == null) return null
            input.copyTo(output)
        }
    }
    Uri.fromFile(file)
} catch (_: Throwable) {
    null

}
