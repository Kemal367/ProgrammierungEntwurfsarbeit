package com.example.programmierungentwurfsarbeit.model

import android.graphics.Bitmap
import android.net.Uri
import com.example.programmierungentwurfsarbeit.R

data class Inspiration(
    val title: String,
    val category: Kategorie,
    val imageRes: Int,
    val description: String
)

data class UserActivity(
    val title: String,
    val category: Kategorie,
    val description: String,
    val imageUri: Uri? = null,
    val cameraPreview: Bitmap? = null,
    val imageRes: Int? = null,
    val durationMinutes: Int,
    val createdAt: Long = System.currentTimeMillis()
)

fun mockDatenInspiration(): List<Inspiration> = listOf(
    Inspiration(
        "4-7-8 Atemtechnik", Kategorie.Meditation, R.drawable.meditating,
        "Atme 4 Sekunden durch die Nase ein, halte den Atem 7 Sekunden und atme 8 Sekunden durch den Mund aus. Diese Technik hilft dir, deinen Geist zu beruhigen."
    ),
    Inspiration(
        "Spaziergang im Grünen", Kategorie.Natur, R.drawable.go_for_a_walk,
        "Die frische Luft und die natürliche Umgebung helfen dir, den Kopf freizubekommen und Stress abzubauen."
    ),
    Inspiration(
        "Dankbarkeits-Tagebuch", Kategorie.DigitalDetox, R.drawable.gratitude_journal,
        "Nimm dir ein paar Minuten Zeit und schreibe drei Dinge auf, für die du dankbar bist. Diese Übung hilft dir, deinen Fokus auf positive Aspekte deines Lebens zu lenken."
    ),
    Inspiration(
        "Schwimmen gehen", Kategorie.Sport, R.drawable.go_for_a_swim,
        "Schwimmen ist nicht nur eine großartige Möglichkeit, den Körper zu trainieren. Die gleichmäßige Bewegung im Wasser hat auch eine beruhigende Wirkung und kann dazu beitragen, Stress abzubauen."
    ),
    Inspiration(
        "Entspannungsbilder", Kategorie.Meditation, R.drawable.meditating2,
        "Schließe die Augen und stelle dir einen Ort vor, an dem du dich besonders wohl und entspannt fühlst. Diese Übung kann dir helfen, Stress abzubauen und deine innere Balance zu finden."
    )
)

/** Mapping: Inspiration -> UserActivity (mit Dauer) */
fun Inspiration.toActivity(durationMinutes: Int) = UserActivity(
    title = title,
    category = category,
    description = description,
    imageRes = imageRes,
    durationMinutes = durationMinutes
)
