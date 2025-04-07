package lv.chernishenko.chucknorrisfacts.ui.listitem

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import lv.chernishenko.chucknorrisfacts.model.ChuckNorrisFact

@Composable
fun ChuckNorrisFactListItem(
    fact: ChuckNorrisFact,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = ShapeDefaults.Medium)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            AsyncImage(
                model = fact.iconUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
            )
            Spacer(
                modifier = Modifier.width(8.dp)
            )
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = fact.value,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ChuckNorrisFactListItemPreview() {
    ChuckNorrisFactListItem(
        fact = ChuckNorrisFact(
            id = "1",
            url = "",
            iconUrl = "",
            value = "Chuck Norris can divide by zero.",
            timestamp = 0L
        )
    )
}