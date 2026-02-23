package com.example.programmierungentwurfsarbeit.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun DurationDialog(
    titleText: String,
    onConfirm: (minutes: Int) -> Unit,
    onDismiss: () -> Unit
) {
    val MAX_MIN = 1440 // maximale Dauer: 24h in Minuten
    var durationText by remember { mutableStateOf("") }

    val minutes = durationText.toIntOrNull()
    // Speicherbutton kann nur geklickt werden, wenn 1..1440 angegebben wurde
    val isValid = minutes != null && minutes in 1..MAX_MIN
    // Fehler für Minuten
    val hasError = minutes != null && (minutes < 1 || minutes > MAX_MIN)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(titleText) },
        text = {
            Column {
                Text("Bitte Dauer in Minuten angeben (1–$MAX_MIN).")
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = durationText,
                    onValueChange = { raw ->
                        // Nur Ziffern erlauben und Länge begrenzen
                        val digits = raw.filter { it.isDigit() }.take(4)
                        // Hard-Cap auf MAX_MIN (wenn Zahl > MAX_MIN -> auf 1440 setzen)
                        val capped = digits.toIntOrNull()
                            ?.let { if (it > MAX_MIN) MAX_MIN.toString() else it.toString() }
                            ?: digits
                        durationText = capped
                    },
                    label = { Text("Dauer (Minuten)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = hasError, // rot markiert bei Fehler
                    supportingText = {
                        if (hasError) Text("Zulässig ist nur ein Wert zwischen 1 und $MAX_MIN.")
                    }
                )
            }
        },
        confirmButton = {
            // Button funktioniert wenn alle Eingaben richtig sind
            TextButton(
                enabled = isValid,
                onClick = { onConfirm(minutes!!) }
            ) { Text("Hinzufügen") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Abbrechen") }
        }
    )
}
