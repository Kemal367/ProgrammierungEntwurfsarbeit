package com.example.programmierungentwurfsarbeit.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.programmierungentwurfsarbeit.R
import com.example.programmierungentwurfsarbeit.model.BottomTab
import com.example.programmierungentwurfsarbeit.model.Inspiration
import com.example.programmierungentwurfsarbeit.model.mockDatenInspiration
import com.example.programmierungentwurfsarbeit.ui.activities.ActivitiesScreen
import com.example.programmierungentwurfsarbeit.ui.activities.ActivitiesViewModel
import com.example.programmierungentwurfsarbeit.ui.home.HomeScreen
import com.example.programmierungentwurfsarbeit.ui.impressum.ImpressumScreen
import com.example.programmierungentwurfsarbeit.ui.theme.appColor
import com.example.programmierungentwurfsarbeit.ui.theme.appNavItemColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(
    // Mock-Daten werden übergeben
    initialInspiration: List<Inspiration>? = null
) {
    // Aktiven Tab merken
    var selectedTab by rememberSaveable { mutableStateOf(BottomTab.Inspiration) }
    // Datenquelle für Inspirationen
    val inspirations = remember(initialInspiration) {
        initialInspiration ?: mockDatenInspiration()
    }

    // ViewModel + UI-Status (Datenbank-Flow)
    val vm: ActivitiesViewModel = viewModel()
    val ctx: Context = LocalContext.current
    val activities by vm.activities.collectAsState()

    // Untere App-Bar
    @Composable
    fun BottomBar() {
        NavigationBar(
            containerColor = appColor
        ) {

            NavigationBarItem(
                selected = selectedTab == BottomTab.Inspiration,
                onClick = { selectedTab = BottomTab.Inspiration },
                icon = {
                    Icon(
                        Icons.Outlined.Favorite,
                        contentDescription = stringResource(R.string.inspiration)
                    )
                },
                label = { Text(stringResource(R.string.inspiration)) },
                colors = appNavItemColors()
            )
            NavigationBarItem(
                selected = selectedTab == BottomTab.Activities,
                onClick = { selectedTab = BottomTab.Activities },
                icon = {
                    Icon(
                        Icons.AutoMirrored.Outlined.List,
                        contentDescription = stringResource(R.string.aktivitaeten)
                    )
                },
                label = { Text(stringResource(R.string.aktivitaeten)) },
                colors = appNavItemColors()
            )
            NavigationBarItem(
                selected = selectedTab == BottomTab.Impressum,
                onClick = { selectedTab = BottomTab.Impressum },
                icon = {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = stringResource(R.string.impressum)
                    )
                },
                label = { Text(stringResource(R.string.impressum)) },
                colors = appNavItemColors()
            )
        }
    }

    // Header mit App-Namen
    @Composable
    fun AppTopBar() {
        Column(Modifier.fillMaxWidth()) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                color = Color(0xFFFFF9C4),
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                CenterAlignedTopAppBar(
                    modifier = Modifier.padding(top = 40.dp),
                    title = {
                        Text(
                            stringResource(R.string.name),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    windowInsets = WindowInsets(0)
                )
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        containerColor = Color.White,
        topBar = {
            AppTopBar()
        },
        bottomBar = { BottomBar() }
    ) { padding ->
        when (selectedTab) {
            // Inspirationen: bei Übernahme zur Aktivitäten-Ansicht wechseln
            BottomTab.Inspiration -> HomeScreen(
                modifier = Modifier.padding(padding),
                items = inspirations,
                onAddFromInspiration = { newItem ->
                    vm.add(ctx, newItem)
                    selectedTab = BottomTab.Activities
                }
            )

            // Aktivitäten: CRUD-Operationen onAdd, onUpdate, onDelete
            BottomTab.Activities -> ActivitiesScreen(
                modifier = Modifier.padding(padding),
                activities = activities,
                onAdd = { vm.add(ctx, it) },
                onUpdate = { idx, updated -> vm.updateAt(ctx, idx, updated) },
                onDelete = { idx -> vm.deleteAt(idx) }
            )

            // Impressum ohne Funktion
            BottomTab.Impressum -> ImpressumScreen(Modifier.padding(padding))

        }
    }
}
