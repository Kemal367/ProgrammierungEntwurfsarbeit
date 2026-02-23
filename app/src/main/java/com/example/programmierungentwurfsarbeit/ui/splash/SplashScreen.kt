package com.example.programmierungentwurfsarbeit.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.programmierungentwurfsarbeit.R
import com.example.programmierungentwurfsarbeit.ui.theme.appColor

@Composable
fun SplashScreen() {
    // Vollflächiger Hintergrund der Splash-Seite
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = appColor
    ) {
        // Zentriert den Inhalt
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            // Logo, Loader und Text
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                // App-Logo
                Image(
                    painter = painterResource(
                        id = R.drawable.logo_healthy_me
                    ),
                    contentDescription = "Logo",
                    modifier = Modifier.size(300.dp)
                )
                Spacer(Modifier.height(22.dp))

                // Lade-Animation (Kreis)
                CircularProgressIndicator()
                Spacer(Modifier.height(11.dp))

                // Begleittext während des Ladens
                Text(
                    "Lade Inhalte…",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
