package com.example.programmierungentwurfsarbeit.ui.impressum

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.programmierungentwurfsarbeit.R

@Composable
fun ImpressumScreen(modifier: Modifier = Modifier) {
    val scroll = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .verticalScroll(scroll)
    ) {
        Text(
            stringResource(R.string.impressum),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(12.dp))

        SectionTitle("Angaben gemäß § 5 TMG:")
        Paragraph(
            "Kemal Sacirovic\n" +
                    "Bastianstraße 12\n" +
                    "13357 Berlin"
        )

        Spacer(Modifier.height(8.dp))
        SectionTitle("Kontakt:")
        Paragraph(
            "Telefon: +49 176 75812016\n" +
                    "E-Mail: Kemalsacirovic@gmx.de"
        )

        Spacer(Modifier.height(8.dp))
        SectionTitle("Verantwortlich für den Inhalt nach § 55 Abs. 2 RStV:")
        Paragraph(
            "Kemal Sacirovic\n" +
                    "Bastianstraße 12 1\n" +
                    "13357 Berlin"
        )

        Spacer(Modifier.height(12.dp))
        Divider()
        Spacer(Modifier.height(12.dp))

        SectionTitle("Haftungsausschluss")
        SubTitle("Haftung für Inhalte:")
        Paragraph(
            "Als Diensteanbieter sind wir gemäß § 7 Abs.1 TMG für eigene Inhalte auf diesen Seiten " +
                    "nach den allgemeinen Gesetzen verantwortlich. Nach §§ 8 bis 10 TMG sind wir als Diensteanbieter " +
                    "jedoch nicht verpflichtet, übermittelte oder gespeicherte fremde Informationen zu überwachen oder " +
                    "nach Umständen zu forschen, die auf eine rechtswidrige Tätigkeit hinweisen. Verpflichtungen zur " +
                    "Entfernung oder Sperrung der Nutzung von Informationen nach den allgemeinen Gesetzen bleiben " +
                    "hiervon unberührt. Eine diesbezügliche Haftung ist jedoch erst ab dem Zeitpunkt der Kenntnis einer " +
                    "konkreten Rechtsverletzung möglich. Bei Bekanntwerden von entsprechenden Rechtsverletzungen werden " +
                    "wir diese Inhalte umgehend entfernen."
        )

        Spacer(Modifier.height(8.dp))
        SubTitle("Haftung für Links:")
        Paragraph(
            "Unser Angebot enthält Links zu externen Websites Dritter, auf deren Inhalte wir keinen Einfluss haben. " +
                    "Deshalb können wir für diese fremden Inhalte auch keine Gewähr übernehmen. Für die Inhalte der " +
                    "verlinkten Seiten ist stets der jeweilige Anbieter oder Betreiber der Seiten verantwortlich. Die " +
                    "verlinkten Seiten wurden zum Zeitpunkt der Verlinkung auf mögliche Rechtsverstöße überprüft. " +
                    "Rechtswidrige Inhalte waren zum Zeitpunkt der Verlinkung nicht erkennbar. Eine permanente inhaltliche " +
                    "Kontrolle der verlinkten Seiten ist jedoch ohne konkrete Anhaltspunkte einer Rechtsverletzung nicht " +
                    "zumutbar. Bei Bekanntwerden von Rechtsverletzungen werden wir derartige Links umgehend entfernen."
        )

        Spacer(Modifier.height(8.dp))
        SubTitle("Urheberrecht:")
        Paragraph(
            "Die durch die Seitenbetreiber erstellten Inhalte und Werke auf diesen Seiten unterliegen dem deutschen " +
                    "Urheberrecht. Die Vervielfältigung, Bearbeitung, Verbreitung und jede Art der Verwertung außerhalb der " +
                    "Grenzen des Urheberrechtes bedürfen der schriftlichen Zustimmung des jeweiligen Autors bzw. Erstellers. " +
                    "Downloads und Kopien dieser Seite sind nur für den privaten, nicht kommerziellen Gebrauch gestattet. " +
                    "Soweit die Inhalte auf dieser Seite nicht vom Betreiber erstellt wurden, werden die Urheberrechte Dritter " +
                    "beachtet. Insbesondere werden Inhalte Dritter als solche gekennzeichnet. Sollten Sie trotzdem auf eine " +
                    "Urheberrechtsverletzung aufmerksam werden, bitten wir um einen entsprechenden Hinweis. Bei Bekanntwerden " +
                    "von Rechtsverletzungen werden wir derartige Inhalte umgehend entfernen."
        )
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
    )
}

@Composable
private fun SubTitle(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium)
    )
}

@Composable
private fun Paragraph(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.bodyMedium
    )
}
