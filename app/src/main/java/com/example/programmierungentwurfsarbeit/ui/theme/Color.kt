package com.example.programmierungentwurfsarbeit.ui.theme

import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val CategoryMeditationAccent = Color(0xFFAB88D2)
val CategoryNaturAccent = Color(0xFFB0E080)
val CategoryDigitalDetoxAccent = Color(0xFFF0B34F)
val CategorySportAccent = Color(0xFFA1E1FF)

val CardWhite = Color(0xFFFFFFFF)

val NavItemIndicator = Color(0xFFFFE19E)
val appColor = Color(0xFFFFF9C4)

@Composable
fun appNavItemColors(): NavigationBarItemColors =
    NavigationBarItemDefaults.colors(
        indicatorColor = NavItemIndicator
    )