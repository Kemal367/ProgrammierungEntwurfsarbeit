package com.example.programmierungentwurfsarbeit.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PagerSection(
    title: @Composable () -> Unit,        // Überschrift-/Titel-Slot (z. B. „Inspiration“)
    pagerState: PagerState,               // Zustand des Pagers
    pageCount: Int,                       // Anzahl der Seiten/Dots
    modifier: Modifier = Modifier,
    pageContent: @Composable (page: Int) -> Unit // Inhalt pro Seite
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        title()
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Horizontales Paging über die Inhalte
            HorizontalPager(state = pagerState) { page ->
                pageContent(page)
            }
        }

        Spacer(Modifier.height(8.dp))
        // Dots unterhalb des Pagers (aktueller Index aus pagerState)
        PunkteIndikator(pageCount, pagerState.currentPage, Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
    }
}
