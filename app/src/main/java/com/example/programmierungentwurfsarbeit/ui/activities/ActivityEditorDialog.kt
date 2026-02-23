package com.example.programmierungentwurfsarbeit.ui.activities

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.programmierungentwurfsarbeit.model.Kategorie
import com.example.programmierungentwurfsarbeit.model.UserActivity
import com.example.programmierungentwurfsarbeit.ui.components.ChosenImagePreview
import com.example.programmierungentwurfsarbeit.util.copyUriToCache

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityEditorDialog(
    title: String,
    initial: UserActivity?,
    onDismiss: () -> Unit,
    onSave: (UserActivity) -> Unit
) {
    val MAX_MIN = 1440 // Obergrenze für die Dauer (24h)

    var vTitle by remember { mutableStateOf(initial?.title ?: "") }
    var vDescription by remember { mutableStateOf(initial?.description ?: "") }
    var vCategory by remember { mutableStateOf(initial?.category ?: Kategorie.Meditation) }
    var vDuration by remember {
        mutableStateOf((initial?.durationMinutes ?: 0).takeIf { it > 0 }?.toString() ?: "")
    }

    val context = LocalContext.current

    // Bildquellen-State (nur eine Quelle wird jeweils genutzt)
    var pickedImageUri by remember { mutableStateOf(initial?.imageUri) }
    var cameraBitmap by remember { mutableStateOf(initial?.cameraPreview) }
    var imageRes by remember { mutableStateOf(initial?.imageRes) }

    // Galerie-Picker: content:// → in App-Cache kopieren → file://-URI
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            val cached = copyUriToCache(context, uri) // sichert Bild unter /cache/images/...
            pickedImageUri = cached ?: uri            // Fallback: original content-URI
            cameraBitmap = null
            imageRes = null
        }
    }

    // Öffnet die Kamera für eine schnelle Vorschau (ein flüchtiges Bild im Arbeitsspeicher).
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bmp ->
        if (bmp != null) {
            cameraBitmap = bmp
            pickedImageUri = null
            imageRes = null
        }
    }

    // Dauer-Validierung (1..1440), Fehlerzustand steuert isError/supportingText
    val minutes = vDuration.toIntOrNull()
    val isValid = minutes != null && minutes in 1..MAX_MIN
    val hasError = minutes != null && (minutes < 1 || minutes > MAX_MIN)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = vTitle, onValueChange = { vTitle = it },
                    label = { Text("Titel") }, singleLine = true, modifier = Modifier.fillMaxWidth()
                )

                // Kategorie-Auswahl (Dropdown)
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = vCategory.label,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Kategorie") },
                        modifier = Modifier
                            .menuAnchor(
                                type = MenuAnchorType.PrimaryNotEditable,
                                enabled = true
                            )
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        Kategorie.values().forEach { c ->
                            DropdownMenuItem(
                                text = { Text(c.label) },
                                onClick = { vCategory = c; expanded = false }
                            )
                        }
                    }
                }

                // Dauer-Eingabe mit Hard-Cap
                OutlinedTextField(
                    value = vDuration,
                    onValueChange = { raw ->
                        val digits = raw.filter { it.isDigit() }.take(4)
                        val capped = digits.toIntOrNull()
                            ?.let { if (it > MAX_MIN) MAX_MIN.toString() else it.toString() }
                            ?: digits
                        vDuration = capped
                    },
                    label = { Text("Dauer max 1440 min (24 h)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    isError = hasError,
                    supportingText = {
                        if (hasError) Text("Zulässig ist nur ein Wert zwischen 1 und $MAX_MIN.")
                    }
                )

                // Bildquellen: Galerie / Kamera
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {
                        galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }) { Text("Galerie") }
                    OutlinedButton(onClick = { cameraLauncher.launch(null) }) { Text("Kamera") }
                }

                // Vorschau der aktuell gewählten Bildquelle
                ChosenImagePreview(
                    pickedImageUri = pickedImageUri,
                    cameraBitmap = cameraBitmap,
                    imageRes = imageRes
                )

                // Beschreibung
                OutlinedTextField(
                    value = vDescription, onValueChange = { vDescription = it },
                    label = { Text("Beschreibung") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            // Speichern nur möglich, wenn Dauer valide ist
            TextButton(
                enabled = isValid,
                onClick = {
                    onSave(
                        UserActivity(
                            title = vTitle.trim(),
                            category = vCategory,
                            description = vDescription.trim(),
                            imageUri = pickedImageUri,
                            cameraPreview = cameraBitmap, // wird im ViewModel/Repo ggf. nach file:// persistiert
                            imageRes = imageRes,
                            durationMinutes = minutes!!,   // hier ist minutes nie null (enabled=isValid)
                            createdAt = initial?.createdAt ?: System.currentTimeMillis()
                        )
                    )
                }
            ) { Text("Speichern") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Abbrechen") } }
    )
}
