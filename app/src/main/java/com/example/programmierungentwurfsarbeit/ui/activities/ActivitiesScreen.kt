package com.example.programmierungentwurfsarbeit.ui.activities

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.programmierungentwurfsarbeit.R
import com.example.programmierungentwurfsarbeit.model.UserActivity
import com.example.programmierungentwurfsarbeit.ui.components.ActivityCard
import com.example.programmierungentwurfsarbeit.ui.components.ActivityImage
import com.example.programmierungentwurfsarbeit.ui.components.PagerSection
import com.example.programmierungentwurfsarbeit.ui.components.SectionTitle
import com.example.programmierungentwurfsarbeit.ui.theme.NavItemIndicator
import com.example.programmierungentwurfsarbeit.util.formatTimestamp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ActivitiesScreen(
    modifier: Modifier = Modifier,
    activities: List<UserActivity>,                    // Liste aus der DB/VM
    onAdd: (UserActivity) -> Unit,                     // Hinzufügen
    onUpdate: (index: Int, updated: UserActivity) -> Unit, // Bearbeiten
    onDelete: (index: Int) -> Unit                     // Löschen
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var editIndex by remember { mutableStateOf<Int?>(null) }
    var deleteIndex by remember { mutableStateOf<Int?>(null) }

    if (activities.isEmpty()) {
        // Pager für den leeren Zustand (eine Seite mit „Los geht's“-Karte)
        val emptyPager = rememberPagerState(initialPage = 0, pageCount = { 1 })

        PagerSection(
            title = { SectionTitle(R.string.aktivitaeten) },
            pagerState = emptyPager,
            pageCount = 1,
            modifier = modifier
        ) {
            Box(Modifier.fillMaxSize()) {
                // „Leere“-Karte mit Klick zum Hinzufügen
                ActivityCard(
                    title = "Auf geht's!",
                    categoryLabel = "ersterSchritt",
                    description = "Trage deine erste Aktivität ein!",
                    imageContent = { m ->
                        Image(
                            painter = painterResource(R.drawable.logo_without_caption),
                            contentDescription = "App-Logo",
                            modifier = m,
                            contentScale = ContentScale.Fit
                        )
                    },
                    highlighted = true,
                    rightChip = null,
                    footerRight = null,
                    onClick = { showAddDialog = true },
                    category = null
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .combinedClickable(onClick = { showAddDialog = true })
                )
            }
        }
    } else {
        // Pager über alle Aktivitäten
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { activities.size })

        // Nach jedem Hinzufügen automatisch zur letzten Seite scrollen
        LaunchedEffect(activities.size) {
            if (activities.isNotEmpty()) pagerState.animateScrollToPage(activities.lastIndex)
        }

        PagerSection(
            title = { SectionTitle(R.string.aktivitaeten) },
            pagerState = pagerState,
            pageCount = activities.size,
            modifier = modifier
        ) { page ->
            val a = activities[page]
            Box(Modifier.fillMaxSize()) {
                ActivityCard(
                    title = a.title,
                    categoryLabel = a.category.label,
                    description = a.description,
                    imageContent = { m ->
                        ActivityImage(
                            imageRes = a.imageRes,
                            imageUri = a.imageUri,
                            cameraBitmap = a.cameraPreview,
                            contentDescription = a.title,
                            modifier = m
                        )
                    },
                    highlighted = pagerState.currentPage == page,
                    rightChip = "${a.durationMinutes} min",          // Dauer oben rechts
                    footerRight = formatTimestamp(a.createdAt),      // Timestamp unten rechts
                    category = a.category
                )

                // Unsichtbares Overlay: kurzer Klick = bearbeiten, langer Klick = löschen
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .combinedClickable(
                            onClick = { editIndex = page },
                            onLongClick = { deleteIndex = page }
                        )
                )
            }
        }
    }

    // FAB unten rechts: öffnet „Neue Aktivität“-Dialog
    Box(modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = NavItemIndicator,
            contentColor = Color.Black,
            shape = RoundedCornerShape(16.dp),
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 12.dp
            )
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Hinzufügen")
        }
    }

    // Dialog: neue Aktivität anlegen
    if (showAddDialog) {
        ActivityEditorDialog(
            title = "Neue Aktivität",
            initial = null,
            onDismiss = { showAddDialog = false },
            onSave = { onAdd(it); showAddDialog = false } // nach Speichern schließen
        )
    }

    // Dialog: vorhandene Aktivität bearbeiten
    editIndex?.let { idx ->
        ActivityEditorDialog(
            title = "Aktivität bearbeiten",
            initial = activities[idx],
            onDismiss = { editIndex = null },
            onSave = { updated ->
                // createdAt als Schlüssel beibehalten
                onUpdate(idx, updated.copy(createdAt = activities[idx].createdAt))
                editIndex = null
            }
        )
    }

    // Bestätigungsdialog: Aktivität löschen
    deleteIndex?.let { idx ->
        AlertDialog(
            onDismissRequest = { deleteIndex = null },
            title = { Text("Aktivität löschen?") },
            text = { Text("Möchtest du „${activities[idx].title}“ wirklich löschen?") },
            confirmButton = {
                TextButton(onClick = { onDelete(idx); deleteIndex = null }) { Text("Löschen") }
            },
            dismissButton = { TextButton(onClick = { deleteIndex = null }) { Text("Abbrechen") } }
        )
    }
}
