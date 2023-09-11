package uk.co.baconi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen(where: String) {
    Box(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)
    ) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            Surface(shape = CircleShape) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp).padding(3.dp, 3.dp, 4.dp, 4.dp)
                )
            }
        }
        Text(
            text = "Loading $where...",
            modifier = Modifier.align(Alignment.Center).offset(0.dp, 70.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}